
log = (args...) ->
    console.log.apply console, args if console.log?
    
class Application extends Backbone.View
	events:
        "click    .testlogin"          : "testlogin"
    testlogin: (e) ->
    	e.preventDefault()
    	@$el.hide()
    	@$el.find('.testlogin').removeClass("btn")
		
$ ->
	app=new Application el: $("#sbpwq")
