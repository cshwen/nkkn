$ ->
log = (args...) ->
	console.log.apply console, args if console.log?
    
class Application extends Backbone.View
    events:
        "click    .btn-success"          : "testlogin"
        "click    .sbpwq"          : "caodan"

    caodan:(e)->
        e.preventDefault()
	alert "test"

    testlogin: (e)->
        e.preventDefault()
    	jsRoutes.controllers.Application.registerUser().ajax
	data: $("#register_form").serialize()
	success: (data) ->
            alert "注册成功！"
	error: (err) ->
            alert "注册失败！"
            @$el.find('.alert-error').html("注册失败")
            $.error("Error: " + err)
	false
app = new Application 
