<form action="${pageContext.request.contextPath}/buyer/placeorder" method="post">
    <input type="hidden" name="productId" value="${product.id}" />
    <input type="submit" value="Buy" />
</form>
