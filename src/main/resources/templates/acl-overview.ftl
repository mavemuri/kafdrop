<#--
 Copyright 2016 Kafdrop contributors.

 Licensed under the Apache License, Version 2.0 (the "License");
 you may not use this file except in compliance with the License.
 You may obtain a copy of the License at

 http://www.apache.org/licenses/LICENSE-2.0

 Unless required by applicable law or agreed to in writing, software
 distributed under the License is distributed on an "AS IS" BASIS,
 WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 See the License for the specific language governing permissions and
 limitations under the License.
-->
<#import "/spring.ftl" as spring />
<#import "lib/template.ftl" as template>
<@template.header "ACLs">
    <style type="text/css">
        .bs-table.overview td {
            white-space: nowrap;
        }

        td.leader-partitions {
            word-break: break-all;
        }
    </style>
</@template.header>

<script src="<@spring.url '/js/powerFilter.js'/>"></script>

<#setting number_format="0">

<h2>ACLs</h2>
<div id="searchFilter">Search filter&nbsp;&nbsp;<INPUT id='filter' size=30 NAME='searchRow' title='Just type to filter the rows'></div>
<br/>
<div id="acl-overview">
    <table class="table table-bordered">
        <thead>
        <tr>
            <th><i class="fa fa-tag"></i>&nbsp;&nbsp;Name</th>
            <th><i class="fa fa-user"></i>&nbsp;&nbsp;DN</th>
            <th><i class="fa fa-database"></i>&nbsp;&nbsp;Topic</th>
            <th><i class="fa fa-tag"></i>&nbsp;&nbsp;Operation</th>
        </tr>
        </thead>
        <tbody>
        <#list acls?sort_by("name", "dn", "topic", "op") as a>
            <tr class="dataRow">
                <td>${a.name}</td>
                <td>${a.dn}</td>
                <td>${a.topic}</td>
                <td>${a.op}</td>
            </tr>
        </#list>
        </tbody>
    </table>
</div>

<@template.footer/>
