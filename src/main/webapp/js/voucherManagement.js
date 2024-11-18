document.addEventListener('DOMContentLoaded', function () {
    const addVoucherForm = document.getElementById('addVoucherForm');
    const editVoucherForm = document.getElementById('editVoucherForm');
    const forms = [addVoucherForm, editVoucherForm];
    forms.forEach(form => {
        if (form) {
            form.addEventListener('submit', function (event) {
                if (!validateForm(this)) {
                    event.preventDefault();
                }
            });
        }
    });

    const editVoucherButtons = document.querySelectorAll('.editVoucherBtn');
    editVoucherButtons.forEach(button => {
        button.addEventListener('click', () => {
            populateEditVoucherModal(button.dataset.voucherId, button.dataset.voucherCode, button.dataset.voucherName, button.dataset.voucherType === 'true', button.dataset.voucherValue, button.dataset.voucherStartdate, button.dataset.voucherEnddate);
        });
    });
});

function populateEditVoucherModal(voucherId, voucherCode, voucherName, type, value, startDate, endDate) {
    const modal = document.getElementById('editVoucherModal');
    modal.querySelector('#editVoucherId').value = voucherId;
    modal.querySelector('#voucherCode').value = voucherCode;
    modal.querySelector('#voucherName').value = voucherName;

    const selectElement = modal.querySelector('#discountType');
    for (let i = 0; i < selectElement.options.length; i++) {
        if (selectElement.options[i].value === String(type ? '1' : '0')) {
            selectElement.options[i].selected = true;
            break;
        }
    }

    const discountValueInput = modal.querySelector('#discountValue');
    discountValueInput.value = value;

    const formatDateTime = (dateString) => {
        const date = new Date(dateString);
        const year = date.getFullYear();
        const month = String(date.getMonth() + 1).padStart(2, '0');
        const day = String(date.getDate()).padStart(2, '0');
        const hours = String(date.getHours()).padStart(2, '0');
        const minutes = String(date.getMinutes()).padStart(2, '0');
        return `${year}-${month}-${day}T${hours}:${minutes}`;
    };

    modal.querySelector('#startDate').value = formatDateTime(startDate);
    modal.querySelector('#endDate').value = formatDateTime(endDate);

    discountTypeSelect.addEventListener('change', function () {
        discountValueInput.value = "";

        if (this.value === '1') {
            discountValueInput.setAttribute('max', '1');
            discountValueInput.step = "0.01";
        } else {
            discountValueInput.removeAttribute('max');
            discountValueInput.step = "any";
        }
    });

    if (type) {
        discountValueInput.setAttribute('max', '1');
        discountValueInput.step = "0.01";
    } else {
        discountValueInput.removeAttribute('max');
        discountValueInput.step = "any";
    }


    const editForm = document.getElementById('editVoucherForm');
    const errorDiv = modal.querySelector('.errorMessage');

    editForm.addEventListener('submit', function (event) {
        event.preventDefault();
        const formData = new FormData(editForm);
        formData.append('action', 'edit');

        const xhr = new XMLHttpRequest();

        xhr.onload = function () {
            if (xhr.status >= 200 && xhr.status < 300) {
                if (xhr.responseURL && xhr.responseURL !== window.location.href) {
                    window.location.href = xhr.responseURL;
                    hideEditVoucherModal();
                } else {
                    window.location.reload();
                }
            } else {
                errorDiv.style.display = 'block';
                try {
                    const errorData = JSON.parse(xhr.responseText);
                    const errorMessages = errorData.errorMessages;
                    let errorMessage = '';
                    if (errorMessages && typeof errorMessages === 'object') {
                        for (const field in errorMessages) {
                            if (errorMessages.hasOwnProperty(field)) {
                                errorMessage += `${errorMessages[field]}<br>`;
                            }
                        }
                    }
                    errorDiv.innerHTML = errorMessage;
                } catch (e) {
                    errorDiv.textContent = "An error occurred during the edit.";
                }
            }
        };

        xhr.onerror = function () {
            errorDiv.style.display = 'block';
            errorDiv.textContent = "An error occurred during the edit.";
        };
        xhr.open('POST', 'VoucherController');
        xhr.send(formData);
    });

    modal.style.display = 'block';
    document.body.classList.add('modal-open');
    const closeButton = modal.querySelector('.btn-close');
    closeButton.addEventListener('click', () => {
        hideEditVoucherModal();
    });
}


function hideEditVoucherModal() {
    const modal = document.getElementById('editVoucherModal');
    modal.classList.remove('show');
    modal.setAttribute('aria-hidden', 'true');
    modal.style.display = 'none';
    const backdrop = document.querySelector('.modal-backdrop');
    if (backdrop) {
        backdrop.remove();
    }
    document.body.classList.remove('modal-open');
}

function validateForm(form) {
    let isValid = true;
    const voucherCodeInput = form.querySelector('[name="voucherCode"]');
    const voucherNameInput = form.querySelector('[name="voucherName"]');
    const discountTypeSelect = form.querySelector('#discountType');
    const valueInput = form.querySelector('[name="value"]');
    const startDateInput = form.querySelector('[name="startDate"]');
    const endDateInput = form.querySelector('[name="endDate"]');
    const voucherCodeError = form.querySelector('.voucherCodeError');
    const voucherNameError = form.querySelector('.voucherNameError');
    const valueError = form.querySelector('.discountValueError');
    const startDateError = form.querySelector('.startDateError');
    const endDateError = form.querySelector('.endDateError');

    const voucherCode = voucherCodeInput.value.trim();
    if (!voucherCode) {
        displayError(voucherCodeInput, voucherCodeError, 'Voucher code is required.');
        isValid = false;
    } else if (!/^[a-zA-Z0-9]+$/.test(voucherCode)) {
        displayError(voucherCodeInput, voucherCodeError, 'Voucher code must be alphanumeric.');
        isValid = false;
    } else {
        clearError(voucherCodeInput, voucherCodeError);
    }

    const voucherName = voucherNameInput.value.trim();
    if (!voucherName) {
        displayError(voucherNameInput, voucherNameError, 'Voucher name is required.');
        isValid = false;
    } else {
        clearError(voucherNameInput, voucherNameError);
    }

    const value = parseFloat(valueInput.value);
    if (discountTypeSelect.value === '1' && value > 1) {
        displayError(valueInput, valueError, 'Percentage value cannot exceed 1.');
        isValid = false;
    } else if (value <= 0) {
        displayError(valueInput, valueError, 'Discount must be a positive number.');
        isValid = false;
    } else {
        clearError(valueInput, valueError);
    }


    const startDate = new Date(startDateInput.value);
    const endDate = new Date(endDateInput.value);
    if (isNaN(startDate)) {
        displayError(startDateInput, startDateError, 'Invalid start date format.');
        isValid = false;
    } else {
        clearError(startDateInput, startDateError);
    }
    if (isNaN(endDate)) {
        displayError(endDateInput, endDateError, 'Invalid end date format.');
        isValid = false;
    } else {
        clearError(endDateInput, endDateError);
    }
    if (!isNaN(startDate) && !isNaN(endDate) && startDate > endDate) {
        displayError(startDateInput, startDateError, 'Start date cannot be after end date.');
        isValid = false;
    }

    return isValid;
}

function displayError(inputElement, errorElement, message) {
    inputElement.classList.add('is-invalid');
    errorElement.textContent = message;
}
function clearError(inputElement, errorElement) {
    inputElement.classList.remove('is-invalid');
    errorElement.textContent = '';
}

function updateDiscountValueFormat() {
    const discountType = document.getElementById("discountType").value;
    const discountValueInput = document.getElementById("discountValue");
    discountValueInput.value = "";
    if (discountType === "1") {
        discountValueInput.setAttribute("max", 1);
        discountValueInput.step = "0.01";
    } else {
        discountValueInput.removeAttribute("max");
        discountValueInput.step = "any";
    }
}