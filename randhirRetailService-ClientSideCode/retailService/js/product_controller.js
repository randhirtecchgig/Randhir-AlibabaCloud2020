'use strict';

angular.module('myApp').controller('ProductController', ['$scope', 'ProductService', function($scope, ProductService) {
    var self = this;
    self.product={productId:'',productType:'',brandName:'',itemId:''};
    self.products=[];

    self.submit = submit;
    self.edit = edit;
    self.remove = remove;
    self.reset = reset;
	self.submitSearch = submitSearch;


    fetchAllProducts();

    function fetchAllProducts(){
        ProductService.fetchAllProducts()
            .then(
            function(d) {
                self.products = d;
            },
            function(errResponse){
                console.error('Error while fetching Products');
            }
        );
    }

    function createProduct(product){
        ProductService.createProduct(product)
            .then(
            fetchAllProducts,
            function(errResponse){
                console.error('Error while creating Product');
            }
        );
    }

    function updateProduct(product, productId){
        ProductService.updateProduct(product, productId)
            .then(
            fetchAllProducts,
            function(errResponse){
                console.error('Error while updating Product');
            }
        );
    }

    function deleteProduct(productId){
        ProductService.deleteproduct(productId)
            .then(
            fetchAllProducts,
            function(errResponse){
                console.error('Error while deleting Product');
            }
        );
    }

    function submit() {
        if(self.product.productId===null){
            console.log('Saving New Product', self.product);
            createProduct(self.product);
        }else{
            updateProduct(self.product, self.product.productId);
            console.log('Product updated with productId ', self.product.productId);
        }
        reset();
    }
	
	function submitSearch() {
        if(self.product.productId===null){
            console.log('Product Id null so fetching all products', self.product);
            fetchAllProducts();
        }else{
            ProductService.searchProduct(self.product.productId)
			.then(
            function(d) {
				self.products=[];
                self.products = d;
            },
            function(errResponse){
                console.error('Error while seraching Products');
            }
        );
            console.log('Product updated with productId ', self.product.productId);
        }
        resetTable();
    }
	
    function edit(productId){
        console.log('productId to be edited', productId);
        for(var i = 0; i < self.products.length; i++){
            if(self.products[i].productId === productId) {
                self.product = angular.copy(self.products[i]);
                break;
            }
        }
    }

    function remove(productId){
        console.log('productId to be deleted', productId);
        if(self.product.productId === productId) {//clean form if the product to be deleted is shown there.
            reset();
        }
        deleteProduct(productId);
    }


    function reset(){
        self.product={productId:null,productType:'',productType:'',brandName:''};
        $scope.myForm.$setPristine(); //reset Form
    }
	
	function resetTable() {
		self.products=[];
	}
}]);
