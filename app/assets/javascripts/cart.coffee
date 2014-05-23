$(".table #update").click (e) ->
  e.preventDefault()
  @id=$(e.target).parent().attr("data-cart-id")
  @number=$(e.target).parent().prev().val()
  r=jsRoutes.controllers.Carts.alterCart(@id, @number)
  $.ajax
    type:r.type
    url:r.url
    success:(data) ->
      updateCartView()
    error:(err) ->
      alert '更新失败，请重试！'

$(".table #clear").click (e) ->
  e.preventDefault()
  @id=$(e.target).parent().attr("data-cart-id")
  r=jsRoutes.controllers.Carts.deleteCart(@id)
  $.ajax
    type:r.type
    url:r.url
    success:(data)->
      updateCartView()
    error:(err)->
      alert '删除失败，请重试！'
  
updateCartView = ()->
  alert 'oooo'