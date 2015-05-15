define([
	"knockout",
	"scripts/championships/championship_model"
], function(ko)  {
	"use strict";

	var ChampionshipViewModel = function() {
		var that = {};

		that.save = function(name) {

			console.log(arguments);
		};
		return that;
	};

	return ChampionshipViewModel;

});