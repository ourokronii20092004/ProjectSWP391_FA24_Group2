function loadProducts() {
    const productTableBody = document.getElementById('productTableBody');
    const url = 'ProductController?action=list';

    fetch(url)
        .then(response => {
            if (!response.ok) {
                throw new Error('Network response was not ok');
            }
            return response.json();
        })
        .then(products => {
            productTableBody.innerHTML = ''; 
            products.forEach(product => {
                        <td>  <div class="btn-group" role="group" aria-label="no">
                            <button class="btn btn-sm btn-warning editProductBtn" 
                                    data-bs-toggle="modal"
                                    data-bs-target="#editProductModal"
                                    data-product-id="${product.productID}"
                                    data-product-name="${product.productName}"
                                    data-product-description="${product.description}"
                                    data-product-price="${product.price}"
                                    data-product-categoryid="${product.categoryID}"
                                    data-product-stockquantity="${product.stockQuantity}"
                                    data-product-imageurl="${product.imageURL}"
                                    data-product-createdat="${product.createdAt}"
                                    data-product-updatedat="${product.updatedAt}">
                                Edit
                            </button>

                            <a href="ProductController?action=delete&productId=${product.productID}"
                               class="btn btn-sm btn-danger"
                               onclick="return confirm('Are you sure you want to delete this product?')">
                                Delete
                            </a> </div>
                        </td>
                    </tr>
                `;
                productTableBody.innerHTML += row; 
            });

            attachEditButtonListeners();
        })
        .catch(error => {
            console.error('Error loading products:', error);
        });
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
            const imageUrl = this.getAttribute('data-product-imageurl');
            const createdAt = this.getAttribute('data-product-createdat');
            const updatedAt = this.getAttribute('data-product-updatedat');
            //
        });
    });
}


document.addEventListener('DOMContentLoaded', function () {
    loadProducts();
});

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