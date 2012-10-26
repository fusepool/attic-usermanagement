<script>
$(function() {
        $( "#tabs" ).tabs();
    });
</script>


<p class="statline ui-state-highlight">There are X amount of users blah blah blah</p>
<div id="tabs">
    <ul>
        <li><a href="#tabs-users">Users</a></li>
        <li><a href="#tabs-groups">Groups</a></li>
        <li><a href="#tabs-permisions">Permissions</a></li>
    </ul>
    <div id="tabs-users">

        Here are the users:<br/>
        <@namespace platform="http://clerezza.org/2009/08/platform#" />
        <@namespace permission="http://clerezza.org/2008/10/permission#" />
        <@namespace sioc="http://rdfs.org/sioc/ns#" />

        <@ldpath path="fn:sort(^rdf:type)">
            <div class="user">
                <a href="/user-management/edit-user?userName=<@ldpath path="platform:userName :: xsd:string"/>" >edit</a>
                User-Name: <@ldpath path="platform:userName :: xsd:string"/><br/>
                <!-- todo add webapp prefix -->

                Permssions: 
                <ul>
                    <@ldpath path="fn:sort(permission:hasPermission)">
                        <li class="permission" style="list-style-type: disc;">
                            <@ldpath path="permission:javaPermissionEntry :: xsd:string"/>
                        </li>
                    </@ldpath>
                </ul>
                
                Groups:
                <ul>
                    <@ldpath path="fn:sort(sioc:has_function)">
                        <li class="permission" style="list-style-type: square;">
                            <@ldpath path="dc:title :: xsd:string"/>
                        </li>
                    </@ldpath>
                </ul>
        <!-- 	<#include "EditableUser.ftl"> -->
            </div>
        </@ldpath>
    </div>
     <div id="tabs-groups">groups</div>
     <div id="tabs-permisions">permissions</div>
</div>
