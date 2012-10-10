Here are the users:<br/>
<@namespace platform="http://clerezza.org/2009/08/platform#" />
<@namespace permission="http://clerezza.org/2008/10/permission#" />
<@namespace sioc="http://rdfs.org/sioc/ns#" />

<@ldpath path="fn:sort(^rdf:type)">
<div class="user">
	User-Name: <@ldpath path="platform:userName :: xsd:string"/><br/>
	<!-- todo add webapp prefix -->
	<a href="/user-management/edit-user?userName=<@ldpath path="platform:userName :: xsd:string"/>" >
	edit
	</a>
	Permssions: <ul>
	<@ldpath path="fn:sort(permission:hasPermission)">
		<li class="permission">
		<@ldpath path="permission:javaPermissionEntry :: xsd:string"/>
		</li>
	</@ldpath>
	</ul>
	Groups:
	<ol>
	<@ldpath path="fn:sort(sioc:has_function)">
		<li class="permission">
		<@ldpath path="dc:title :: xsd:string"/>
		</li>
	</@ldpath>
	</ol>
	<#include "included.ftl">
</div>
</@ldpath>

</div>
</div>
</body>
</html>
