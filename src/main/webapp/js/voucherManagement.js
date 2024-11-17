

document.addEventListener('DOMContentLoaded', function () {

    // Edit Voucher Modal Functionality
    const editVoucherButtons = document.querySelectorAll('.editVoucherBtn');
    editVoucherButtons.forEach(button => {
        button.addEventListener('click', () => {
            const voucherId = button.dataset.voucherId;
            const voucherCode = button.dataset.voucherCode;
            const voucherName = button.dataset.voucherName;
            const type = button.dataset.voucherType === 'true';
            const value = button.dataset.voucherValue;
            const startDate = button.dataset.voucherStartdate;
            const endDate = button.dataset.voucherEnddate;
            const isActive = button.dataset.voucherIsactive === 'true';

            populateEditVoucherModal(voucherId, voucherCode, voucherName, type, value, startDate, endDate, isActive);
        });
    });



    const addVoucherForm = document.querySelector('#addVoucherModal form');
    const editVoucherForm = document.querySelector('#editVoucherModal form');

    if (addVoucherForm) {
        addVoucherForm.addEventListener('submit', (event) => {
            if (!validateVoucherForm(event.target)) {
                event.preventDefault();
            }
        });
    }

    if (editVoucherForm) {
        editVoucherForm.addEventListener('submit', (event) => {
            if (!validateVoucherForm(event.target)) {
                event.preventDefault();
            }
        });
    }
});




function populateEditVoucherModal(voucherId, voucherCode, voucherName, type, value, startDate, endDate, isActive) {
    const modal = document.getElementById('editVoucherModal');
    modal.querySelector('[name="voucherID"]').value = voucherId;
    modal.querySelector('[name="voucherCode"]').value = voucherCode;
    modal.querySelector('[name="voucherName"]').value = voucherName;
    modal.querySelector('[name="type"]').value = type;
    modal.querySelector('[name="value"]').value = value;
    modal.querySelector('[name="startDate"]').value = startDate;
    modal.querySelector('[name="endDate"]').value = endDate;
    modal.querySelector('[name="isActive"]').checked = isActive;

    modal.style.display = 'block';
    document.body.classList.add('modal-open');

    const closeButton = modal.querySelector('.btn-close');
    closeButton.addEventListener('click', () => {
        hideEditVoucherModal();
    });

}

function hideEditVoucherModal() {

    const modal = document.getElementById('editVoucherModal');
    modal.style.display = 'none';
    document.body.classList.remove('modal-open');


}

function validateVoucherForm(form) {
    let isValid = true;

    const voucherCodeInput = form.querySelector('[name="voucherCode"]');
    const voucherCode = voucherCodeInput.value.trim();

    if (!voucherCode) {
        alert('Voucher code is required.');
        voucherCodeInput.focus();
        isValid = false;
        return isValid;
    }

    if (!/^[a-zA-Z0-9]+$/.test(voucherCode)) {
        alert('Voucher code must be alphanumeric.');
        voucherCodeInput.focus();
        isValid = false;
        return isValid;
    }


    const voucherNameInput = form.querySelector('[name="voucherName"]');
    const voucherName = voucherNameInput.value.trim();
    if (!voucherName) {
        alert("Voucher Name is required.");
        voucherNameInput.focus();
        isValid = false;
        return isValid;
    }

    const valueInput = form.querySelector('[name="value"]');
    const value = parseFloat(valueInput.value);
    if (isNaN(value) || value <= 0) {
        alert("Value must be a positive number.");
        valueInput.focus();
        isValid = false;
        return isValid;
    }


    const startDateInput = form.querySelector('[name="startDate"]');
    const endDateInput = form.querySelector('[name="endDate"]');

    const startDate = new Date(startDateInput.value);
    const endDate = new Date(endDateInput.value);



    if (isNaN(startDate.getTime())) {
        alert("Invalid Start Date format. Use YYYY-MM-DD.");
        startDateInput.focus();
        isValid = false;
        return isValid;
    }
    if (isNaN(endDate.getTime())) {
        alert("Invalid End Date format. Use YYYY-MM-DD.");
        endDateInput.focus();
        isValid = false;
        return isValid;
    }





    if (startDate > endDate) {
        alert("Start Date cannot be after End Date.");
        startDateInput.focus();
        isValid = false;
        return isValid;
    }



    return isValid;
}
