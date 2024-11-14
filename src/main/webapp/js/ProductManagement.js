document.addEventListener('DOMContentLoaded', function () {
    fetchCategoriesAndUpdateSelects(function () {
        fetchCategoriesAndUpdateRestoreProductTable();
        attachEditButtonListeners();
        attachFormValidation();
        modalTextareaHeightListener();
    });

});

function modalTextareaHeightListener() {
    const editModal = document.getElementById('editProductModal');
    const addModal = document.getElementById('addProductModal');

    if (editModal) {
        editModal.addEventListener('shown.bs.modal', resizeTextarea);
        editModal.querySelector('input[name="image"]').addEventListener('change', resizeTextarea);
        document.getElementById('editProduct_imagePreview').addEventListener('load', resizeTextarea);
    }

    if (addModal) {
        addModal.addEventListener('shown.bs.modal', resizeTextarea);
        addModal.querySelector('input[name="image"]').addEventListener('change', resizeTextarea);
        document.getElementById('addProduct_imagePreview').addEventListener('load', resizeTextarea);
    }
}

function resizeTextarea(event) {
    const modal = event.target ? event.target.closest('.modal') : event.currentTarget.closest('.modal');

    if (modal) {
        const textarea = modal.querySelector('textarea[name="description"]');
        if (textarea) {
            const modalBody = textarea.closest('.modal-body');

            //
            window.requestAnimationFrame(() => {
                textarea.style.height = 'auto';
                textarea.style.height = (modalBody.clientHeight - 135) + 'px';
            });
        }
    }
}




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
            const imageURL = this.getAttribute('data-product-imageurl');

            const preview = document.getElementById('editProduct_imagePreview');
            if (imageURL) {
                preview.src = imageURL;
                preview.classList.remove("d-none");
            } else {
                preview.src = "/img/pro/default.jpg"; //place holder
                preview.classList.add("d-none"); //hide -> no img
            }

        });
    });
}


function previewImage(input, previewId) {
    const preview = document.getElementById(previewId);
    const file = input.files[0];
    const reader = new FileReader();

    

    reader.onloadend = function () {
        preview.src = reader.result;
        preview.classList.remove("d-none"); //show preview
    };

    if (file) {
        reader.readAsDataURL(file);
    } else {
        preview.src = "/img/pro/default.jpg"; //place holder
        preview.classList.add("d-none"); //hide -> no img
    }
    
    preview.addEventListener('load', function (event) {
        resizeTextarea(event);
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
    return combinedPercentage <= 20;
}

function isValidPrice(price) {
    return price >= 0 && price <= 10000000;
}

function isValidStockQuantity(stockQuantity) {
    return stockQuantity >= 0 && stockQuantity <= 10000000;
}

function isValidImageType(contentType) {
    const allowedTypes = ["image/jpeg", "image/png", "image/jpg"];
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

function updateProductTable(categoryData) {
    const tableRows = document.querySelectorAll("#productTableBody tr");

    tableRows.forEach(row => {
        const categoryIdCell = row.querySelector(".category-id");
        if (categoryIdCell) {
            const categoryId = categoryIdCell.textContent;
            const categoryName = findCategoryName(categoryData, categoryId);
            categoryIdCell.textContent = categoryName;
        }
    });
}

function findCategoryName(categories, categoryId) {
    const categoryIdNumber = parseInt(categoryId, 10);

    for (let i = 0; i < categories.length; i++) {
        if (categories[i].categoryId === categoryIdNumber) {
            return categories[i].categoryName;
        }
    }

    console.warn("Category ID not found:", categoryId);
    return categoryId; // return the original ID if a name isn't found
}


function fetchCategoriesAndUpdateSelects(callback) {
    console.log("Fetching categories using XHR for selects...");
    const xhr = new XMLHttpRequest();
    xhr.open('GET', 'ProductController?action=getCategories');
    xhr.onload = function () {
        if (xhr.status >= 200 && xhr.status < 300) {
            try {
                const categoryData = JSON.parse(xhr.response);

                //add modal
                const addCategorySelect = document.getElementById('addProduct_categoryId');
                if (addCategorySelect) {
                    populateCategorySelect(addCategorySelect, categoryData);
                }

                //edit modal
                const editCategorySelect = document.getElementById('editProduct_categoryId');
                if (editCategorySelect) {
                    populateCategorySelect(editCategorySelect, categoryData);
                }


                updateProductTable(categoryData);
                updateRestoreProductTable(categoryData);
                if (callback)
                    callback();
            } catch (e) {
                console.error('Error parsing category data (XHR):', e.message, xhr.response);
                alert("Error loading categories: Invalid data received.");
            }
        } else {
            console.error(`XHR error! Status: ${xhr.status}, Response: ${xhr.response}`);
            alert("Error loading Categories. Bad status: " + xhr.status);
        }
    };
    xhr.onerror = function () {
        console.error("XHR request failed");
        alert("Error loading Categories: Network or server error.");
    };
    xhr.send();
}



function populateCategorySelect(selectElement, categories) {
    selectElement.innerHTML = ''; //clear existing options

    const defaultOption = document.createElement('option');
    defaultOption.value = '';
    defaultOption.text = 'Select Category';
    selectElement.appendChild(defaultOption);


    categories.forEach(category => {
        const option = document.createElement('option');
        option.value = category.categoryId;
        option.text = category.categoryName;
        selectElement.appendChild(option);
    });
}



function fetchCategoriesAndUpdateRestoreProductTable() {
    const xhr = new XMLHttpRequest();
    xhr.open('GET', 'ProductController?action=getCategories');
    xhr.onload = function () {
        if (xhr.status >= 200 && xhr.status < 300) {
            try {
                const categoryData = JSON.parse(xhr.response);
                updateRestoreProductTable(categoryData);
            } catch (e) {
                console.error('Error parsing category data:', e.message, xhr.response);
                alert("Error loading category names: JSON parse failed. Please refresh the page.");
            }

        } else {
            console.error(`XHR error! Status: ${xhr.status}, Response: ${xhr.response}`);
            alert("Error loading category names. Bad status: " + xhr.status);
        }
    };
    xhr.onerror = function () {
        console.error("XHR request failed");
        alert("Error loading category names: Unknown.");
    };
    xhr.send();
}

function updateRestoreProductTable(categoryData) {
    const tableRows = document.querySelectorAll("#productListTable tbody tr");
    tableRows.forEach(row => {
        const categoryIdCell = row.querySelector(".category-cell");
        if (categoryIdCell) {
            const categoryId = categoryIdCell.textContent;
            const categoryName = findCategoryName(categoryData, categoryId);
            categoryIdCell.textContent = categoryName;
        }
    });
}