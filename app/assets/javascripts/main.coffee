
log = (args...) ->
	console.log.apply console, args if console.log?
    
$('#_process').click (e)->
	e.preventDefault()
	$.ajax
		type:'POST'
		url:$('#_form').attr('action')
		data: 
			username: $("#_form input[name=username]").val()
			password: $("#_form input[name=password]").val()
			email: $("#_form input[name=email]").val()
		success: (data) ->
			alert "success"
			window.location.replace('/')
		
		error:(err)->
			setTimeout ->
				alert '网络出错，请稍候重试。', 3000
				
$("#_form input[name=username]").blur  (e) ->
		@username=$("#_form input[name=username]").val()
		r=jsRoutes.controllers.Application.isExistUser(@username)
		$.ajax
			type: r.type
			url: r.url
			success:(data) ->
				alert ' yes!'+data
			error:(err)->
				alert err.responseText
		e.preventDefault()
