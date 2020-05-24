'use strict';

angular.module('myApp').factory('ProductService', ['$http', '$q', function($http, $q){

   // var REST_SERVICE_URI = 'http://149.129.145.27:8080/employeeList';
   // var REST_SERVICE_URI = 'http://localhost:8080/retailService/productList.json';
    var REST_SERVICE_URI = 'http://149.129.145.27:8080/retailService/productList.json';
	//var REST_SERVICE_URI_SEARCH = 'http://localhost:8080/retailService/product.json';
	var REST_SERVICE_URI_SEARCH = 'http://149.129.145.27:8080/retailService/product.json';
    var factory = {
        fetchAllProducts: fetchAllProducts,
        createProduct: createProduct,
        updateProduct:updateProduct,
        deleteProduct:deleteProduct,
		searchProduct:searchProduct
    };

    return factory;
	
	function fetchAllProducts() {
		var deferred = $q.defer();
			$http.get(REST_SERVICE_URI)
				.then(
				function (response) {
					deferred.resolve(response.data);
				},
				function(errResponse){
					console.error('Error while fetching Users');
					deferred.reject(errResponse);
				}
			);
			return deferred.promise;
	}
	
    function fetchAllEmployees() {
        var deferred = $q.defer();
		var req = {
				method: 'GET',
				url: REST_SERVICE_URI,
				headers: {
					'Accept': 'application/json',
					'Access-Control-Allow-Methods': 'POST, GET, OPTIONS, DELETE, PUT',
					'Access-Control-Allow-Origin': '*',
					'Access-Control-Allow-Headers': "X-Requested-With, Content-Type, Origin, Authorization, Accept, Client-Security-Token, Accept-Encoding",
					'Content-Type': 'application/json'
				}
				//data: { test: 'test' }
			}
        $http.get(req)
            .then(
            function (response) {
                deferred.resolve(response.data);
            },
            function(errResponse){
                console.error('Error while fetching Products');
                deferred.reject(errResponse);
            }
        );
        return deferred.promise;
    }

    function createProduct(product) {
        var deferred = $q.defer();
        $http.post(REST_SERVICE_URI, product)
            .then(
            function (response) {
                deferred.resolve(response.data);
            },
            function(errResponse){
                console.error('Error while creating Product');
                deferred.reject(errResponse);
            }
        );
        return deferred.promise;
    }


    function updateProduct(product, productId) {
        var deferred = $q.defer();
        $http.put(REST_SERVICE_URI+productId, product)
            .then(
            function (response) {
                deferred.resolve(response.data);
            },
            function(errResponse){
                console.error('Error while updating Product');
                deferred.reject(errResponse);
            }
        );
        return deferred.promise;
    }

    function deleteProduct(productId) {
        var deferred = $q.defer();
        $http.delete(REST_SERVICE_URI+productId)
            .then(
            function (response) {
                deferred.resolve(response.data);
            },
            function(errResponse){
                console.error('Error while deleting Product');
                deferred.reject(errResponse);
            }
        );
        return deferred.promise;
    }
	
	function searchProduct(productId) {
        var deferred = $q.defer();
        //$http.get(REST_SERVICE_URI_SEARCH+"/"+productId)
		$http.get(REST_SERVICE_URI_SEARCH)
            .then(
            function (response) {
                deferred.resolve(response.data);
            },
            function(errResponse){
                console.error('Error while searching product');
                deferred.reject(errResponse);
            }
        );
        return deferred.promise;
    }

}]);
