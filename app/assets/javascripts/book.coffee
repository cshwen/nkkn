$("#buy").click (e) ->
  e.preventDefault()
  @id=$(e.target).parent().attr("data-book-id")
  @num=$(e.target).prev().val()
  r=jsRoutes.controllers.Carts.auCart(@id,@num)
  $.ajax
    type: r.type
    url: r.url
    success:(data) ->
      updateCartView()
    error:(err)->

updateCartView = ()->
  r=jsRoutes.controllers.Carts.view()
  $.ajax
    type: r.type
    url: r.url
    success:(data) ->
      $('#usercart').html(data)
    error:(err)->
      alert 'no'
