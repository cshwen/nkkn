$("a[id=update]").click  (e) ->
  updateClick(e)
  
updateClick=(e)->
  e.preventDefault()
  @id=$(e.target).parent().attr("data-cart-id")
  @number=$(e.target).parent().prev().val()
  r=jsRoutes.controllers.Carts.alterCart(@id, @number)
  $.ajax
    type:r.type
    url:r.url
    success:(data) ->
      refreshCartConsole()
      refreshCartView()
    error:(err) ->
      refreshCartView()
      alert '更新失败，请重试！'

$("#cartConsole #delete").click (e) ->
  clearClick(e)

clearClick=(e)->
  e.preventDefault()
  @id=$(e.target).parent().attr("data-cart-id")
  r=jsRoutes.controllers.Carts.deleteCart(@id)
  $.ajax
    type:r.type
    url:r.url
    success:(data)->
      refreshCartConsole()
      refreshCartView()
    error:(err)->
      refreshCartView()
      alert '删除失败，请重试！'
  
refreshCartConsole = ()->
  r=jsRoutes.controllers.Carts.refresh()
  $.ajax
    type: r.type
    url: r.url
    success:(data) ->
      $('#cartConsole').html(data)
    error:(err)->
      alert 'no'
    
refreshCartView = ()->
  r=jsRoutes.controllers.Carts.view()
  $.ajax
    type: r.type
    url: r.url
    success:(data) ->
      $('#cartView').html(data)
      $("a[id=update]").bind 'click', (e)=>updateClick(e)
      $("#cartConsole #clear").bind 'click', (e)=>clearClick(e)
    error:(err)->
      alert 'no'
      
$("#cartConsole #clear").click (e) ->
  r=jsRoutes.controllers.Carts.clearCart()
  $.ajax
    type:r.type
    url:r.url
    success:(data)->
      alert '已清空，返回首页'
      window.location.replace('/')
    error:(err)->
      alert '清空失败，请重试！'