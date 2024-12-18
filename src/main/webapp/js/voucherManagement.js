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

    const discountTypeSelect = document.getElementById('discountType');
    if (discountTypeSelect) {
        discountTypeSelect.addEventListener('change', updateDiscountValueFormat);
    }
});

function formatDateForDatetimeLocal(date) {
    if (!(date instanceof Date)) {
        date = new Date(date);
    }
    if (isNaN(date.getTime())) {
        console.error("Invalid date provided to formatDateForDatetimeLocal");
        return "";
    }
    const year = date.getFullYear();
    const month = String(date.getMonth() + 1).padStart(2, '0');
    const day = String(date.getDate()).padStart(2, '0');
    const hours = String(date.getHours()).padStart(2, '0');
    const minutes = String(date.getMinutes()).padStart(2, '0');
    return `${year}-${month}-${day}T${hours}:${minutes}`;
}

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

    modal.querySelector('#discountValue').value = value;
    modal.querySelector('#startDate').value = formatDateForDatetimeLocal(startDate);
    modal.querySelector('#endDate').value = formatDateForDatetimeLocal(endDate);

    const discountTypeSelectEdit = document.getElementById('discountType');
    if (discountTypeSelectEdit) {
        discountTypeSelectEdit.addEventListener('change', updateDiscountValueFormat);
    }

    const discountValueInput = modal.querySelector('#discountValue');
    if (type) {
        discountValueInput.setAttribute('max', '1');
        discountValueInput.step = "0.01";
    } else {
        discountValueInput.removeAttribute('max');
        discountValueInput.step = "any";
    }

    const editForm = document.getElementById('editVoucherForm');
    const errorDiv = modal.querySelector('.errorMessage');



        const editModal = new bootstrap.Modal(document.getElementById('editVoucherModal'));
    editModal.show();

}

function validateForm(form) {
    let isValid = true;
    const voucherCodeInput = form.querySelector('[name="voucherCode"]');
    const voucherNameInput = form.querySelector('[name="voucherName"]');
    const discountTypeSelect = form.querySelector('#discountType');
    const valueInput = form.querySelector('[name="discountValue"]');
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