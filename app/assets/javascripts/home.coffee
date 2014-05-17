$(".caption a").click (e) ->
  e.preventDefault()
  @id=$(e.target).attr("data-book-id")
  r=jsRoutes.controllers.Carts.addCart(@id)
  $.ajax
    type: r.type
    url: r.url
    success:(data) ->
      $('#bookModal div p').html(data)
      $('#bookModal div p').addClass("text-success")
      $('#bookModal div p').removeClass("text-error")
    error:(err)->
      $('#bookModal div p').html("网络出错，请重试")
      $('#bookModal div p').addClass("text-error")
      $('#bookModal div p').removeClass("text-success")
      

