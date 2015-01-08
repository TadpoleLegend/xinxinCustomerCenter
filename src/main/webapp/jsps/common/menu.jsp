<%@ page contentType="text/html;charset=gbk"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<nav class="site-nav" id="nav">
	<div class="row">
		<ul id="dropdown" data-bind="foreach: menuList">
			<li><a data-bind="attr : { 'href' : url, 'title' : title, 'id': elementId}"><b data-bind="text : title"></b></a></li>
		</ul>
	</div>
</nav>
<script>
	$(document).ready(function() {
		
		var MenuModel = function() {
			var self = this;
			
			self.menuList = ko.observableArray([]);
			self.loadMenus = function() {
				$.ajax({
					url : '/ls/findAllMenus.ls',
					success : function(data) {
						self.menuList(data);
						
						activeCurrentMenuItem();
					}
				});
			};
			
			self.loadMenus();
		};
		var menuModel = new MenuModel();
		var $menuContainer = $("#nav")[0];
		ko.applyBindings(menuModel, $menuContainer);
		
	});
</script>