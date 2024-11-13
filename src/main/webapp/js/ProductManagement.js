document.addEventListener('DOMContentLoaded', function () {
    attachEditButtonListeners();
    attachFormValidation();
});

function attachEditButtonListeners() {
    const editButtons = document.querySelectorAll('.editProductBtn');
    editButtons.forEach(button => {
        button.addEventListener('click', function (event) {
            const modal = document.getElementById('editProductModal');
            const form = modal.querySelector('form');

            const productId = this.getAttribute('data-product-id');
            const productName = this.getAttribute('data-product-name');
            const description = this.getAttribute('data-product-description');
            const price = this.getAttribute('data-product-price');
            const categoryId = this.getAttribute('data-product-categoryid');
            const stockQuantity = this.getAttribute('data-product-stockquantity');

            form.querySelector('[name="productId"]').value = productId;
            form.querySelector('[name="productName"]').value = productName;
            form.querySelector('[name="description"]').value = description;
            form.querySelector('[name="price"]').value = price;
            form.querySelector('[name="categoryId"]').value = categoryId;
            form.querySelector('[name="stockQuantity"]').value = stockQuantity;
        });
    });
}

function attachFormValidation() {
    const addProductForm = document.querySelector('#addProductModal form');
    const editProductForm = document.querySelector('#editProductModal form');

    if (addProductForm) {
        addProductForm.addEventListener('submit', function (event) {
            event.preventDefault();

            if (!validateProductForm(this)) {
                return;
            } else {
                this.submit();
            }
        });
    }

    if (editProductForm) {
        editProductForm.addEventListener('submit', function (event) {
            event.preventDefault();

            if (!validateProductForm(this)) {
                return;
            } else {
                this.submit();
            }
        });
    }
}

function validateProductForm(form) {
    const productNameInput = form.querySelector('[name="productName"]');
    const priceInput = form.querySelector('[name="price"]');
    const categoryIdInput = form.querySelector('[name="categoryId"]');
    const stockQuantityInput = form.querySelector('[name="stockQuantity"]');
    const imageInput = form.querySelector('[name="image"]');

    const productName = productNameInput.value;
    if (!isValidProductName(productName)) {
        alert("Invalid product name. At least 1 letter & 80% valid characters.");
        return false;
    }

    const price = parseFloat(priceInput.value);
    if (!isValidPrice(price)) {
        alert("Price must be between 0 and 10,000,000.");
        return false;
    }

    const categoryId = parseInt(categoryIdInput.value);


    const stockQuantity = parseInt(stockQuantityInput.value);

    if (!isValidStockQuantity(stockQuantity)) {
        alert("Stock quantity must be between 0 and 10,000,000.");
        return false;
    }

    const imageFile = imageInput.files[0];
    if (imageFile && !isValidImageType(imageFile.type)) {
        alert("Invalid image type. Upload JPG, JPEG, or PNG.");
        return false;
    }

    return true;
}

function isValidProductName(productName) {
    if (typeof productName !== 'string') {
        return false;
    }

    if (productName === null || productName.trim() === "") {
        return false;
    }

    if (!/[a-zA-Z]/.test(productName)) {
        return false;
    }

    let specialAndNumberCount = 0;
    for (let i = 0; i < productName.length; i++) {
        const char = productName.charAt(i);
        if (!((char >= 'a' && char <= 'z') || (char >= 'A' && char <= 'Z') || char === ' ')) {
            specialAndNumberCount++;
        }
    }

    const combinedPercentage = (specialAndNumberCount / productName.length) * 100;
    return combinedPercentage <= 40;
}

function isValidPrice(price) {
    return price >= 0 && price <= 10000000;
}

function isValidStockQuantity(stockQuantity) {
    return stockQuantity >= 0 && stockQuantity <= 10000000;
}

function isValidImageType(contentType) {
    const allowedTypes = ["image/jpeg", "image/png"];
    return allowedTypes.includes(contentType);
}

function searchProduct() {
    var input, filter, table, tr, td, i, txtValue;
    input = document.getElementById("productSearchInput");
    filter = input.value.toUpperCase();
    table = document.getElementById("productListTable");
    tr = table.getElementsByTagName("tr");
    for (i = 0; i < tr.length; i++) {
        td = tr[i].getElementsByTagName("td")[2];
        if (td) {
            txtValue = td.textContent || td.innerText;
            if (txtValue.toUpperCase().indexOf(filter) > -1) {
                tr[i].style.display = "";
            } else {
                tr[i].style.display = "none";
            }
        }
    }

}

