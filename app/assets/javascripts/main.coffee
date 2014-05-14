$ ->
  emailRegEx = new RegExp(/^((?!\.)[a-z0-9._%+-]+(?!\.)\w)@[a-z0-9-\.]+\.[a-z.]{2,5}(?!\.)\w$/i)
  emptyRegEx = new RegExp(/[-_.a-zA-Z0-9]{3,}/)
  
  errorMessages = []
  inputs = []
  emails = []
  pwds = []
  
  errorMessages[$('#username').attr("id")] = "用户名应该为4-16个字符."
  errorMessages[$('#password').attr("id")] = "密码应该为4-16个字符"
  errorMessages[$('#password_repeat').attr("id")] = "上下密码不一致."
  errorMessages[$('#email').attr("id")] = "Email无效. ex: infogmail.com"
  
  # 添加要过滤的所有表单元素
  allinputs = $(".validate").filter(":input")
  for input in allinputs
    if $(input).hasClass("text")
      inputs.push($(input))
    if $(input).hasClass("email")
      emails.push($(input))
    if $(input).hasClass("pwd")
      pwds.push($(input))

  for input in inputs
    input.blur () ->
      validateInputs($(this), emptyRegEx)
      isExistUser()
  for input in pwds
    input.blur () ->
      validateInputs($(this), emptyRegEx)
      validateRepeatPwd()
  for email in emails
    email.blur () ->
      validateInputs($(this), emailRegEx)
   
  # 校验表单
  validateForm = () ->
    $.extend(badFields = [], validateInputs(inputs, emptyRegEx), validateInputs(emails, emailRegEx)   )
    if badFields.length is 0
      return true
    else
      return false
  
  # 上下文密码校验
  validateRepeatPwd = () ->
    error = []
    pwd=$(".register_form #password")
    re_pwd=$(".register_form #password_repeat")
    if $(pwd).val() isnt $(re_pwd).val()
      addErrorStyle(re_pwd)
      error.push($(re_pwd).attr("id"))
    else
      removeErrorStyle(re_pwd)
    return error
    
  # 校验输入值
  validateInputs = (inputs, regex) ->
    error = []
    for input in inputs
      if regex.test($(input).val())
        removeErrorStyle(input)
      else
        error.push($(input).attr("id"))
        addErrorStyle(input)
    return error

  # 添加错误格式
  addErrorStyle = (element) ->
    $(element).prev('label').find('.text-error').html(errorMessages[$(element).attr("id")])
  
  #删除错误格式
  removeErrorStyle = (element) ->
    $(element).prev('label').find('.text-error').html("")

  # 是否存在用户
  isExistUser = () ->
    @username=$("#_form input[name=username]").val()
    r=jsRoutes.controllers.Application.isExistUser(@username)
    sign=false
    $.ajax
      type: r.type
      url: r.url
      async: false
      success:(data) ->
        $("#_form input[name=username]").prev('label').find('.text-error').html(data)
        sign = true
      error:(err)->
        $("#_form input[name=username]").prev('label').find('.text-error').html(err.responseText)
        sign = false
    return sign

  # 表单提交
  $('.register_form').submit (e)->
    e.preventDefault()
    if validateForm()==true and isExistUser()==true
      $.ajax
        type:'POST'
        url:$('#_form').attr('action')
        data:
          username: $("#_form input[name=username]").val()
          password: $("#_form input[name=password]").val()
          email: $("#_form input[name=email]").val()
        success: (data) ->
          alert "注册成功，跳转到首页。"
          window.location.replace('/')
        error:(err)->
          setTimeout ->
            alert '网络出错，请稍候重试。', 3000
  