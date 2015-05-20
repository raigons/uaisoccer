define([
	"knockout"
], function(ko)  {
	"use strict";
	var personViewModel = {
			firstName : "John",
			lastName : "Smith"
		};
	ko.applyBindings(personViewModel);
});