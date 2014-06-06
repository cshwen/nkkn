$(".pagination li").click  (e) ->
  @kw=$(e.target).attr("data-kw")
  @pg=$(e.target).attr("data-pg")
  redirect(routes.Books.osearch(@kw,@pg))
