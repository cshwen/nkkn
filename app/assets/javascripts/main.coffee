
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
		
		error:(err)->
			alert 'sb'