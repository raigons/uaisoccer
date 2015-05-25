define([
	"knockout"
], function(ko)  {
	"use strict";

	var ChampionshipViewModel = function() {
		var that = {};
		that.championshipName = ko.observable(championshipName);

		that.save = function() {
			console.log(that.championshipName + "was saved.");
		};


  // that.initialize: function(data) {
  //       this.name(data.firstName);
  //   },
  //   revert: function() {
  //       this.initialize(this.initialize.originalData());
  //   },
  //   save: function() {
  //       this.initialize.originalData(ko.toJS(this));  


		return that;
	};

	return ChampionshipViewModel;

});




// var model = new ChampionshipViewModel({
//     name: ""
//  });
// ko.applyBindings(model);