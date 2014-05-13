$ ->	
	emailRegEx = new RegExp(/^((?!\.)[a-z0-9._%+-]+(?!\.)\w)@[a-z0-9-\.]+\.[a-z.]{2,5}(?!\.)\w$/i)
	emptyRegEx = new RegExp(/[-_.a-zA-Z0-9]{3,}/)
	
	errorMessages = []
	inputs = []
	emails = []
	
	
	errorMessages[$('#username').attr("id")] = "用户名应该为4-16个字符."
	errorMessages[$('#password').attr("id")] = "密码应该为4-16个字符"
	errorMessages[$('#password_repeat').attr("id")] = "上下密码不一致."
	errorMessages[$('#email').attr("id")] = "Email无效. ex: infogmail.com"
	
	allinputs = $(".validate").filter(":input")
	for input in allinputs
		if $(input).hasClass("text")
			inputs.push($(input))
		if $(input).hasClass("email")
			emails.push($(input))

	for input in inputs
		input.blur () ->
			validateInputs($(this), emptyRegEx)
	for email in emails
		email.blur () ->
			validateInputs($(this), emailRegEx)
			
	validateForm = () ->
		$.extend(badFields = [], validateInputs(inputs, emptyRegEx), validateInputs(emails, emailRegEx)  )
		if badFields.length is 0
			valid = true
		else
			valid = false
		return valid

	validateInputs = (inputs, regex) ->
		error = []
		for input in inputs
			if regex.test($(input).val())
				removeErrorStyle(input)
			else
				error.push($(input).attr("id"))
				addErrorStyle(input)
		return error
		
	addErrorStyle = (element) ->
		$(element).addClass('form-error')
		$(element).prev('label').find('.text-error').html(errorMessages[$(element).attr("id")])
	
	removeErrorStyle = (element) ->
		$(element).removeClass('form-error')
		$(element).prev('label').find('.text-error').html("")

$('.register_form').submit ->
	alert validateForm()
	