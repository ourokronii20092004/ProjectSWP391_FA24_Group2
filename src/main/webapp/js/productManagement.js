/* global fetch */
document.addEventListener('DOMContentLoaded', function () {
    attachEditButtonListeners();
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
            const imageUrl = this.getAttribute('data-product-imageurl');

            form.querySelector('[name="productId"]').value = productId;
            form.querySelector('[name="productName"]').value = productName;
            form.querySelector('[name="description"]').value = description;
            form.querySelector('[name="price"]').value = price;
            form.querySelector('[name="categoryId"]').value = categoryId;
            form.querySelector('[name="stockQuantity"]').value = stockQuantity;
        });
    });
}

// Search product
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