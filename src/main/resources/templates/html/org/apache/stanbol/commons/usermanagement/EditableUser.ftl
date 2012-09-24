<@namespace platform="http://clerezza.org/2009/08/platform#" />
<@namespace permission="http://clerezza.org/2008/10/permission#" />
<@namespace sioc="http://rdfs.org/sioc/ns#" />

<form>
User-Name: <input type="text" value="<@ldpath path="platform:userName :: xsd:string"/>" /><br/>
<#assign mbox>
<@ldpath path="foaf:mbox" />
</#assign>
<#assign email>
<#if mbox != "">${mbox?substring(7)}</#if>
</#assign>
Email : <input type="text" value="${email}" /><br/>

	Permssions: <ul>
	<@ldpath path="fn:sort(permission:hasPermission)">
		<#assign permission>
		<@ldpath path="permission:javaPermissionEntry :: xsd:string"/>
		</#assign>
		<li class="permission">
		<input type="text" value="${permission?html}" />
		</li>
	</@ldpath>
	</ul>
	<button name="addPermission">Add permission</button>
	Groups:
	<ol>
	<@ldpath path="fn:sort(sioc:has_function)">
		<li class="permission">
		<@ldpath path="dc:title :: xsd:string"/>
		</li>
	</@ldpath>
	</ol>

</form>