<@namespace platform="http://clerezza.org/2009/08/platform#" />
<@namespace permission="http://clerezza.org/2008/10/permission#" />
<@namespace sioc="http://rdfs.org/sioc/ns#" />

<script>
$(function() {
    $("#tabs").tabs();
    showUserList();
});

function showUserList(){  
    $("div#tabs-users").html($("#user-list").html());
    $("#user-table").tablesorter();
}

function editUser(name){
    var back = ("<div style='float:right;'><href='#' onClick='showUserList()'>&lt;&lt; back to user list</a></div>");    
    $.ajax({
        url: '/user-management/edit-user?userName='+name,
        success: function(data) {
            $("div#tabs-users").html(back);
            $("div#tabs-users").append(data);
        }
    });
}

function removeUser(name){
    $.dialog({
        resizable: false,
        height:140,
        modal: true,
        buttons: {
            "Delete User": function() {
                $( this ).dialog( "close" );
            },
            Cancel: function() {
                $( this ).dialog( "close" );
            }
        }
    });
}



</script>


<p class="statline ui-state-highlight">There are X amount of users blah blah blah</p>
<div id="tabs">
    <ul>
        <li><a href="#tabs-users">Users</a></li>
        <li><a href="#tabs-groups">Groups</a></li>
        <li><a href="#tabs-permisions">Permissions</a></li>
    </ul>
    <div id="tabs-users">loading User List</div>
    <div id="tabs-groups">groups</div>
    <div id="tabs-permisions">permissions</div>
</div>


<div id="temp" style="display:none">
    <div id="user-list">
        <table id="user-table" class="nicetable noauto">
            <thead><tr><th>Name</th><th>login</th><th>email</th><th>groups</th><th>&nbsp;</th></tr></thead>
            <tbody>
                <@ldpath path="fn:sort(^rdf:type)">
                <#assign userName>
                    <@ldpath path="platform:userName :: xsd:string"/>
                </#assign>
                <tr>
                    <td>get full name here</td>
                    <td>${userName}</td>

                    <#assign mbox>
                    <@ldpath path="foaf:mbox" />
                    </#assign>
                    <#assign email>
                    <#if mbox != "">${mbox?substring(7)}</#if>
                    </#assign>
                    <td>${email}</td>

                    <td>
                    <@ldpath path="fn:sort(sioc:has_function)">
                       <@ldpath path="dc:title :: xsd:string"/>
                    </@ldpath>
                    </td>
                    <td>
                        <ul class="icons ui-widget">
                            <li class="dynhover ui-state-default ui-corner-all" title="Edit" onClick="javascript:editUser('${userName}')"><span class="ui-icon ui-icon-edit">&nbsp;</span></li>
                            <li class="dynhover ui-state-default ui-corner-all" title="delete" onClick="javascript:removeUser('${userName}')"><span class="ui-icon ui-icon-trash">&nbsp;</span></li>
                        </ul>
                    </td>
                </tr>
                </@ldpath>
            </tbody>
        </table>
    </div>
</div>
<!--
                Permssions: 
                <ul>
                    <@ldpath path="fn:sort(permission:hasPermission)">
                        <li class="permission" style="list-style-type: disc;">
                            <@ldpath path="permission:javaPermissionEntry :: xsd:string"/>
                        </li>
                    </@ldpath>
                </ul>
-->