// app.js
(function($){

		var FullTextSearch = {};
		window.FullTextSearch = FullTextSearch;
		
		var template = function(name) {
			return Mustache.compile($('#'+name+'-template').html());
		};
		
		FullTextSearch.HomeView = Backbone.View.extend({
			tagName : "form",
			el : $("#main"),
			
			events : {
				"submit" : "findJobs"
			},
			
			render : function(){
				console.log("rendering home page..");
				$("#results").empty();
				return this;
			},
			
			findJobs : function(event){
				console.log('in findJobs()...');
				event.preventDefault();
				$("#results").empty();
				$("#fullTextSearchForm").mask("Finding Jobs ...");
				var query = this.$("#query").val();
			
				console.log("query : "+query);
				
				var self = this;
				$.get("api/jobs/fulltext/"+query  , function (results){ 
			           $("#fullTextSearchForm").unmask();
			           self.renderResults(results,self);
			     });
			
			
			},

			
			renderResults : function(results,self){
				_.each(results,function(result){
					self.renderJob(result);
				});
				
			},
			
			renderJob : function(result){
				var jobView = new FullTextSearch.JobView({result : result});
				$("#results").append(jobView.render().el);
			},
			
			

		});
		
		FullTextSearch.JobView = Backbone.View.extend({
				template : template("job"),
				initialize  : function(options){
					this.result = options.result;
				},
		
				render : function(){
					this.$el.html(this.template(this));
					return this;
				},
				jobtitle : function(){
					return (this.result['jobTitle']);
				},
				address : function(){
					return (this.result['formattedAddress']);
				},
				skills : function(){
					return (this.result['skills']);
				},
				company : function(){
					return ((this.result['company'])['companyName']);
				}
				
		
		});
		
		
		FullTextSearch.Router = Backbone.Router.extend({
			el : $("#main"),
			
			routes : {
				"" : "showHomePage"
			},
			showHomePage : function(){
				console.log('in home page...');
				var homeView = new FullTextSearch.HomeView();
				this.el.append(homeView.render().el);
			}
		
		});
		
		var app = new FullTextSearch.Router();
		Backbone.history.start();
		
		
})(jQuery);