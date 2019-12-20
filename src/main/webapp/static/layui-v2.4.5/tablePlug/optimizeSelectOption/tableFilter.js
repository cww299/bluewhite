layui.define(["table", "form", "laydate", "util", "excel", "laytpl"],
function(e) {
    var R, q, J, l, a, G = layui.jquery,
    V = layui.table,
    H = layui.form,
    p = layui.laydate,
    $ = layui.laytpl,
    y = layui.util,
    K = layui.excel,
    E = {},
    j = {},
    Y = "layui-hide",
    i = 1,
    Q = {},
    Z = {},
    ee = {},
    z = {
        eq: "等于",
        ne: "≠ 不等于",
        gt: "> 大于",
        ge: "≥ 大于等于",
        lt: "< 小于",
        le: "≤ 小于等于",
        contain: "包含",
        notContain: "不包含",
        start: "以...开头",
        end: "以...结尾",
        null: "为空",
        notNull: "不为空"
    },
    b = {
        all: "全部",
        yesterday: "昨天",
        thisWeek: "本周",
        lastWeek: "上周",
        thisMonth: "本月",
        thisYear: "今年"
    };
    e("tableFilter", {
        destroy: function(e) {
            if (e) if (Array.isArray(e)) for (var i = 0; i < e.length; i++) t(e[i]);
            else t(e);
            function t(e) {
                if (e) {
                    var i = e.config.id;
                    G("#soul-filter-list" + i).remove(),
                    G("#soulCondition" + i).remove(),
                    G("#soulDropList" + i).remove(),
                    delete E[i],
                    delete Q[i],
                    delete ee[i]
                }
            }
        },
        clearFilter: function(e) {
            "string" == typeof e && (e = ee[e]),
            Q[e.id] && Q[e.id].filterSos && "{}" !== Q[e.id].filterSos && (delete Q[e.id], this.soulReload(e, !0))
        },
        render: function(g) {
            var x, u, k = this,
            t = G(g.elem),
            e = t.next().children(".layui-table-box").children(".layui-table-main"),
            i = t.next().children(".layui-table-box").children(".layui-table-header").children("table"),
            l = t.next().children(".layui-table-box").children(".layui-table-fixed-l").children(".layui-table-header").children("table"),
            a = t.next().children(".layui-table-box").children(".layui-table-fixed-r").children(".layui-table-header").children("table"),
            C = g.id,
            w = k.getCompleteCols(g.cols),
            o = g.filter && g.filter.items || ["column", "data", "condition", "editCondition", "excel"],
            d = !1,
            n = !1,
            s = void 0 === g.excel || !(!g.excel || void 0 !== g.excel.on && !g.excel.on) && g.excel;
            for (x = 0; x < w.length; x++) w[x].field && w[x].filter && (d = !0, 0 === i.find('th[data-field="' + w[x].field + '"]').children().children(".soul-table-filter").length && (n = !0, 0 < i.find('th[data-field="' + w[x].field + '"]').children().children(".layui-table-sort").length ? (i.find('th[data-field="' + w[x].field + '"]').children().children(".layui-table-sort").hide(), i.find('th[data-field="' + w[x].field + '"]').children().append('<span class="layui-table-sort soul-table-filter layui-inline" data-column="' + w[x].field + '" lay-sort="' + i.find('th[data-field="' + w[x].field + '"]').children().children(".layui-table-sort").attr("lay-sort") + '" ' + (void 0 === w[x].filter.split ? "": 'data-split="' + w[x].filter.split + '"') + '><i class="soul-icon soul-icon-filter"></i><i class="soul-icon soul-icon-filter-asc"></i><i class="soul-icon soul-icon-filter-desc"></i></span>')) : i.find('th[data-field="' + w[x].field + '"]').children().append('<span class="soul-table-filter layui-inline" data-column="' + w[x].field + '" ' + (void 0 === w[x].filter.split ? "": 'data-split="' + w[x].filter.split + '"') + '><i class="soul-icon soul-icon-filter"></i><i class="soul-icon soul-icon-filter-asc"></i><i class="soul-icon soul-icon-filter-desc"></i></span>'), 0 < l.find('th[data-field="' + w[x].field + '"]').children().children(".layui-table-sort").length ? (l.find('th[data-field="' + w[x].field + '"]').children().children(".layui-table-sort").hide(), l.find('th[data-field="' + w[x].field + '"]').children().append('<span class="layui-table-sort soul-table-filter layui-inline" data-column="' + w[x].field + '" lay-sort="' + l.find('th[data-field="' + w[x].field + '"]').children().children(".layui-table-sort").attr("lay-sort") + '" ' + (void 0 === w[x].filter.split ? "": 'data-split="' + w[x].filter.split + '"') + '><i class="soul-icon soul-icon-filter"></i><i class="soul-icon soul-icon-filter-asc"></i><i class="soul-icon soul-icon-filter-desc"></i></span>')) : l.find('th[data-field="' + w[x].field + '"]').children().append('<span class="soul-table-filter layui-inline" data-column="' + w[x].field + '" ' + (void 0 === w[x].filter.split ? "": 'data-split="' + w[x].filter.split + '"') + '><i class="soul-icon soul-icon-filter"></i><i class="soul-icon soul-icon-filter-asc"></i><i class="soul-icon soul-icon-filter-desc"></i></span>'), 0 < a.find('th[data-field="' + w[x].field + '"]').children().children(".layui-table-sort").length ? (a.find('th[data-field="' + w[x].field + '"]').children().children(".layui-table-sort").hide(), a.find('th[data-field="' + w[x].field + '"]').children().append('<span class="layui-table-sort soul-table-filter layui-inline" data-column="' + w[x].field + '" lay-sort="' + a.find('th[data-field="' + w[x].field + '"]').children().children(".layui-table-sort").attr("lay-sort") + '" ' + (void 0 === w[x].filter.split ? "": 'data-split="' + w[x].filter.split + '"') + '><i class="soul-icon soul-icon-filter"></i><i class="soul-icon soul-icon-filter-asc"></i><i class="soul-icon soul-icon-filter-desc"></i></span>')) : a.find('th[data-field="' + w[x].field + '"]').children().append('<span class="soul-table-filter layui-inline" data-column="' + w[x].field + '" ' + (void 0 === w[x].filter.split ? "": 'data-split="' + w[x].filter.split + '"') + '><i class="soul-icon soul-icon-filter"></i><i class="soul-icon soul-icon-filter-asc"></i><i class="soul-icon soul-icon-filter-desc"></i></span>')));
            if (ee[g.id] = g, Z[g.id] = d) {
                if ((!g.filter || void 0 === g.filter.bottom || g.filter.bottom) && 0 === t.next().children(".soul-bottom-contion").length) {
                    t.next().children(".layui-table-box").after('<div class="soul-bottom-contion"><div class="condition-items"></div><div class="editCondtion"><a class="layui-btn layui-btn-primary">编辑筛选条件</a></div></div>');
                    var r = t.next().children(".layui-table-box").children(".layui-table-body").outerHeight() - t.next().children(".soul-bottom-contion").outerHeight();
                    g.page && t.next().children(".layui-table-page").hasClass("layui-hide") && (r += t.next().children(".layui-table-page").outerHeight()),
                    t.next().children(".layui-table-box").children(".layui-table-body").css("height", r);
                    var c = r - k.getScrollWidth(e[0]),
                    h = e.children("table").height();
                    t.next().children(".layui-table-box").children(".layui-table-fixed").children(".layui-table-body").css("height", c <= h ? c: "auto"),
                    t.next().children(".soul-bottom-contion").children(".condition-items").css("width", t.next().children(".soul-bottom-contion").width() - t.next().children(".soul-bottom-contion").children(".editCondtion").width() + "px"),
                    t.next().children(".soul-bottom-contion").children(".editCondtion").children("a").on("click",
                    function() {
                        k.showConditionBoard(g)
                    })
                }
                if (!n || E[g.id] || g.isSoulFrontFilter) return E[g.id] = !1,
                g.isSoulFrontFilter = !1,
                void this.bindFilterClick(g);
                if (!g.url && g.page && g.data && g.data.length > g.limit && layui.each(g.data,
                function(e, i) {
                    i[g.indexName] = e
                }), g.url && !g.page ? j[g.id] = layui.table.cache[g.id] : j[g.id] = g.data || layui.table.cache[g.id], g.filter && g.filter.clearFilter) {
                    if (g.where && g.where.filterSos && 0 < JSON.parse(g.where.filterSos).length) return g.where.filterSos = "[]",
                    Q[g.id] = g.where || {},
                    void k.soulReload(g, !1);
                    Q[g.id] = g.where || {}
                } else {
                    if ((void 0 === g.url || !g.page || void 0 === g.where.filterSos) && Q[g.id] && 0 < JSON.parse(Q[g.id].filterSos || "[]").length) return g.where.filterSos = Q[g.id].filterSos,
                    Q[g.id] = g.where,
                    void k.soulReload(g, !1);
                    Q[g.id] = g.where || {}
                }
                if (0 === G("#soul-filter-list" + C).length) { (void 0 === g.soulSort || g.soulSort) && (void 0 === t.attr("lay-filter") && t.attr("lay-filter", C), V.on("sort(" + t.attr("lay-filter") + ")",
                    function(e) {
                        g.url && g.page ? (Q[g.id].field = e.field, Q[g.id].order = e.type, E[g.id] = !0, V.render(G.extend(g, {
                            initSort: e,
                            where: Q[g.id],
                            page: {
                                curr: 1
                            }
                        }))) : !g.url && g.page && ("asc" === e.type ? j[g.id] = layui.sort(j[g.id], e.field) : "desc" === e.type ? j[g.id] = layui.sort(j[g.id], e.field, !0) : j[g.id] = layui.sort(j[g.id], g.indexName), g.initSort = e, g.page = {
                            curr: 1
                        },
                        k.soulReload(g, !1))
                    }));
                    var f = [],
                    p = {
                        column: '<li class="soul-column"><i class="layui-icon layui-icon-table"></i> 表格列 <i class="layui-icon layui-icon-right" style="float: right"></i></li>',
                        data: '<li class="soul-dropList"><i class="soul-icon soul-icon-drop-list"></i> 筛选数据 <i class="layui-icon layui-icon-right" style="float: right"></i></li>',
                        condition: '<li class="soul-condition"><i class="soul-icon soul-icon-query"></i> 筛选条件 <i class="layui-icon layui-icon-right" style="float: right"></i></li>',
                        editCondition: '<li class="soul-edit-condition"><i class="layui-icon layui-icon-edit"></i> 编辑筛选条件 </li>',
                        excel: '<li class="soul-export"><i class="soul-icon soul-icon-download"></i> 导出excel </li>',
                        clearCache: '<li class="soul-clear-cache"><i class="layui-icon layui-icon-delete"></i> 清除缓存 </li>'
                    };
                    for (f.push('<div id="soul-filter-list' + C + '"><form action="" class="layui-form" lay-filter="orm"><ul id="main-list' + C + '" style="display: none">'), f.push('<li class="soul-sort" data-value="asc" ><i class="soul-icon soul-icon-asc"></i> 升序排列 </li>'), f.push('<li class="soul-sort" data-value="desc"  style="border-bottom: 1px solid #e6e6e6"><i class="soul-icon soul-icon-desc"></i> 降序排列 </li>'), x = 0; x < o.length; x++)("excel" !== o[x] || s) && f.push(p[o[x]]);
                    f.push('</ul><ul id="soul-columns' + C + '" style="display: none;">');
                    var y = {};
                    for (x = 0; x < w.length; x++)"checkbox" !== w[x].type && w[x].field ? (f.push('<li data-value="' + w[x].field + '" data-key="' + x + '"><input type="checkbox" value="' + (w[x].field || x) + '" title="' + w[x].title + '" data-fixed="' + (w[x].fixed || "") + '" lay-skin="primary" lay-filter="changeColumns' + C + '" ' + (w[x].hide ? "": "checked") + "></li>"), w[x].filter && w[x].filter.type && (w[x].filter.field ? y[w[x].filter.field] = w[x].filter.type: y[w[x].field] = w[x].filter.type)) : f.push('<li class="layui-hide"><input type="checkbox" title="' + w[x].title + '" /></li>');
                    2 !== JSON.stringify(y).length && (g.where.tableFilterType = JSON.stringify(y)),
                    f.push('</ul><div id="soul-dropList' + C + '" style="display: none"><div class="filter-search"><input type="text" placeholder="关键字搜索" class="layui-input"></div><div class="check"><div class="multiOption" data-type="all"><i class="soul-icon">&#xe623;</i> 全选</div><div class="multiOption" data-type="none"><i class="soul-icon">&#xe63e;</i> 清空</div><div class="multiOption" data-type="reverse"><i class="soul-icon">&#xe614;</i>反选</div></div><ul></ul></div>'),
                    f.push('<ul id="soul-condition' + C + '" style="display: none;"></ul></form></div>'),
                    G("body").append(f.join(""));
                    var v = !0;
                    H.on("checkbox(changeColumns" + C + ")",
                    function(e) {
                        v = !1,
                        e.elem.checked ? (t.next().children(".layui-table-box").children(".layui-table-header").find("thead>tr>th[data-field=" + e.value + "]").removeClass(Y), t.next().children(".layui-table-box").children(".layui-table-body").find("tbody>tr>td[data-field=" + e.value + "]").removeClass(Y), t.next().children(".layui-table-total").find("tbody>tr>td[data-field=" + e.value + "]").removeClass(Y), G(e.elem).data("fixed") && t.next().children(".layui-table-box").children(".layui-table-fixed-" + G(e.elem).data("fixed").substr(0, 1)).find("[data-field=" + e.value + "]").removeClass(Y)) : (t.next().children(".layui-table-box").children(".layui-table-header").find("thead>tr>th[data-field=" + e.value + "]").addClass(Y), t.next().children(".layui-table-box").children(".layui-table-body").find("tbody>tr>td[data-field=" + e.value + "]").addClass(Y), t.next().children(".layui-table-total").find("tbody>tr>td[data-field=" + e.value + "]").addClass(Y), G(e.elem).data("fixed") && t.next().children(".layui-table-box").children(".layui-table-fixed-" + G(e.elem).data("fixed").substr(0, 1)).find("[data-field=" + e.value + "]").addClass(Y));
                        var i = [].concat.apply([], g.cols);
                        for (x = 0; x < i.length; x++) i[x].field && i[x].field === e.value && (i[x].hide = !e.elem.checked);
                        k.resize(g),
                        g.filter && g.filter.cache && localStorage.setItem(location.pathname + location.hash + g.id, k.deepStringify(g.cols)),
                        t.next().children(".layui-table-box").children(".layui-table-body").children("table").children("tbody").children("tr.childTr").children("td").attr("colspan", t.next().children(".layui-table-box").children(".layui-table-header").find("thead>tr>th:visible").length)
                    }),
                    G("#soul-columns" + C + ">li[data-value]").on("click",
                    function() {
                        G(this).find(":checkbox").is(":disabled") || (v && G(this).find("div.layui-form-checkbox").trigger("click"), v = !0)
                    }),
                    G("#soul-dropList" + C + " .check [data-type]").on("click",
                    function() {
                        switch (G(this).data("type")) {
                        case "all":
                            G(this).parents("#soul-dropList" + C).find("input[type=checkbox]:not(:checked)").prop("checked", !0);
                            break;
                        case "reverse":
                            G(this).parents("#soul-dropList" + C).find("input[type=checkbox]").each(function() {
                                G(this).prop("checked", !G(this).prop("checked"))
                            });
                            break;
                        case "none":
                            G(this).parents("#soul-dropList" + C).find("input[type=checkbox]:checked").prop("checked", !1)
                        }
                        return H.render("checkbox", "orm"),
                        k.updateDropList(g, G("#main-list" + C).data("field")),
                        !1
                    }),
                    G("#soul-dropList" + C + " .filter-search input").on("input",
                    function() {
                        var e = G(this).val();
                        "" === e ? G("#soul-dropList" + C + ">ul>li").show() : (G("#soul-dropList" + C + ">ul>li").hide(), G("#soul-dropList" + C + '>ul>li[data-value*="' + e.toLowerCase() + '"]').show())
                    }),
                    G("#main-list" + C + " .soul-column").on("mouseover",
                    function(e) {
                        var i, t;
                        k.hideDropList(g),
                        k.hideCondition(g),
                        e.stopPropagation(),
                        R && clearTimeout(R),
                        G("#soul-columns" + C).show(),
                        t = G(this).parent().offset().left + G(this).parent().width() + G("#soul-columns" + C).width() < document.body.clientWidth ? (i = G(this).parent().offset().left + G(this).parent().width(), "fadeInLeft") : (i = G(this).parent().offset().left - G("#soul-columns" + C).width(), "fadeInRight"),
                        G("#soul-columns" + C).css({
                            top: G(this).offset().top,
                            left: i
                        }).removeClass().addClass(t + " animated")
                    }),
                    G("#main-list" + C + " .soul-dropList").on("mouseover",
                    function(e) {
                        if (G("#soul-dropList" + C).is(":visible") && !G("#soul-dropList" + C).hasClass("fadeOutLeft")) return ! 1;
                        k.hideColumns(g),
                        k.hideCondition(g),
                        e.stopPropagation(),
                        q && clearTimeout(q),
                        G("#soul-dropList" + C + ">.filter-search>input").val(""),
                        G("#soul-dropList" + C).show();
                        var i, t, l = G("#main-list" + C).data("field");
                        t = G("#main-list" + C).offset().left + G("#soul-dropList" + C).width() + G("#soul-dropList" + C).width() < document.body.clientWidth ? (i = G("#main-list" + C).offset().left + G("#main-list" + C).width(), "fadeInLeft") : (i = G("#main-list" + C).offset().left - G("#soul-dropList" + C).width(), "fadeInRight"),
                        G("#soulDropList" + C).find("." + l + "DropList li input[type=checkbox]:checked").prop("checked", !1);
                        var a = Q[g.id] || {},
                        o = JSON.parse(a.filterSos ? a.filterSos: null),
                        d = "",
                        n = "";
                        if (o) for (x = 0; x < o.length; x++) if (o[x].head && "in" === o[x].mode && o[x].field === l) {
                            for (d = o[x].id, n = o[x].prefix, u = 0; u < o[x].values.length; u++) G("#soulDropList" + C).find("." + l + 'DropList li input[type=checkbox][value="' + o[x].values[u] + '"]').prop("checked", !0);
                            break
                        }
                        G("#soul-dropList" + C + ">ul").data({
                            head: !0,
                            id: d,
                            prefix: n,
                            refresh: !0,
                            split: G("#main-list" + C).data("split")
                        }).html(G("#soulDropList" + C).find("." + l + "DropList li").clone()),
                        G("#soul-dropList" + C).css({
                            top: G(this).offset().top,
                            left: i
                        }).show().removeClass().addClass(t + " animated"),
                        setTimeout(function() {
                            G("#soul-dropList" + C + ">.filter-search>input").focus(),
                            H.render("checkbox", "orm")
                        },
                        1);
                        var s = !0;
                        H.on("checkbox(soulDropList" + C + ")",
                        function(e) {
                            s = !1,
                            k.updateDropList(g, l)
                        }),
                        G("#soul-dropList" + C + ">ul>li[data-value]").on("click",
                        function() {
                            s && G(this).find("div.layui-form-checkbox").trigger("click"),
                            s = !0
                        })
                    }),
                    G("#main-list" + C + " .soul-condition").on("mouseover",
                    function(e) {
                        if (G("#soul-condition" + C).is(":visible") && !G("#soul-condition" + C).hasClass("fadeOutLeft")) return ! 1;
                        k.hideColumns(g),
                        k.hideDropList(g),
                        e.stopPropagation(),
                        J && clearTimeout(J);
                        var i = document.body.clientWidth;
                        G("#soul-condition" + C).show();
                        var t, l, n = G(this).parent().data("field");
                        l = G(this).parent().offset().left + G(this).parent().width() + G("#soul-condition" + C).width() < i ? (t = G(this).parent().offset().left + G(this).parent().width(), "fadeInLeft") : (t = G(this).parent().offset().left - G("#soul-condition" + C).width(), "fadeInRight");
                        var s, a = [],
                        o = Q[g.id] || {},
                        d = JSON.parse(o.filterSos ? o.filterSos: null);
                        if (d) for (x = 0; x < d.length; x++) if (d[x].head && d[x].field === n && ("date" === d[x].mode || "group" === d[x].mode)) {
                            s = d[x];
                            break
                        }
                        var r = G(this).parent().data("type");
                        if (k.startsWith(r, "date")) k.showDate(g, n, s, l, G(this).offset().top, G(this).parent().offset().left + G(this).parent().width(), "down", !0);
                        else {
                            var u = {};
                            for (x = 0; x < w.length; x++) w[x].field && (u[w[x].field] = w[x].title);
                            var c = "<select lay-filter='conditionChange'>";
                            for (var h in z) c += '<option value="' + h + '">' + z[h] + "</option>";
                            if (c += "</select>", a.push('<table class="condition-table"><tbody>'), s && s.children && 0 < s.children.length) for (x = 0; x < s.children.length; x++) {
                                var f = s.children[x].id,
                                p = s.children[x].prefix,
                                y = s.children[x].type,
                                v = s.children[x].value;
                                for (var h in a.push('<tr data-id="' + f + '">'), 0 === x ? a.push('<td class="soul-condition-title">' + u[n] + "</td>") : a.push('<td>   <div>      <input type="checkbox" name="switch" lay-filter="soul-coondition-switch" lay-skin="switch" lay-text="与|或" ' + (p && "and" !== p ? "": "checked") + ">    </div></td>"), a.push('<td style="width: 110px;"><div class="layui-block" ><select lay-filter="conditionChange">'), z) a.push('<option value="' + h + '" ' + (h === y ? "selected": "") + ">" + z[h] + "</option>");
                                a.push("</select></div></td>"),
                                a.push('<td style="width: 110px;"><div class="layui-block" ><input class="layui-input value" value="' + (v || "") + '" placeholder="值" /></div></td>'),
                                a.push('<td><i class="layui-icon layui-icon-delete del" style="font-size: 23px; color: #FF5722; cursor: pointer"></i></td>'),
                                a.push("</tr>")
                            } else a.push('<tr data-id="" data-type="eq" data-value=""><td class="soul-condition-title">' + u[n] + '</td><td style="width: 110px;"><div class="layui-block" >' + c + '</div></td><td style="width: 110px;"><div class="layui-block" ><input class="layui-input value" placeholder="值" /></div></td><td><i class="layui-icon layui-icon-delete del" style="font-size: 23px; color: #FF5722; cursor: pointer"></i></td></tr>');
                            function m(e) {
                                var i = e.data("id"),
                                t = G("#soul-condition" + C).data("id"),
                                l = e.find('input[lay-filter="soul-coondition-switch"]:checked').prop("checked") ? "and": "or",
                                a = e.find("select").val(),
                                o = e.find(".value").val(),
                                d = G("#soul-condition" + C).data("head");
                                s = t ? {
                                    id: i,
                                    prefix: l,
                                    mode: "condition",
                                    field: n,
                                    type: a,
                                    value: o,
                                    groupId: t
                                }: {
                                    head: d,
                                    prefix: "and",
                                    mode: "group",
                                    field: n,
                                    children: [{
                                        id: k.getDifId(),
                                        prefix: l,
                                        mode: "condition",
                                        field: n,
                                        type: a,
                                        value: o,
                                        groupId: t
                                    }]
                                },
                                k.updateWhere(g, s),
                                t ? i || e.data("id", s.id) : (G("#soul-condition" + C).data("id", s.id), e.data("id", s.children[0].id))
                            }
                            function b(e) {
                                var i;
                                1 === G(e).parents("table:eq(0)").find("tr").length ? (i = G("#soul-condition" + C).data("id"), G("#soul-condition" + C).data("id", ""), G(e).parents("tr:eq(0)").find("select").val("eq"), G(e).parents("tr:eq(0)").find(".value").val("").show(), H.render("select", "orm")) : (i = G(e).parents("tr:eq(0)").data("id"), 0 === G(e).parents("tr:eq(0)").index() && G(e).parents("table:eq(0)").find("tr:eq(1)>td:eq(0)").html(u[n]).addClass("soul-condition-title"), G(e).parents("tr:eq(0)").remove()),
                                i && k.updateWhere(g, {
                                    id: i,
                                    delete: !0
                                })
                            }
                            a.push('</tbody></table><div style="text-align: center; padding-top: 5px"><button class="layui-btn layui-btn-sm" data-type="add"><i class="layui-icon">&#xe654;</i>添加</button><span style="display: inline-block;width: 50px"></span><button class="layui-btn layui-btn-sm" data-type="search"><i class="layui-icon">&#xe615;</i>查询</button></div>'),
                            G("#soul-condition" + C).data({
                                head: !0,
                                id: s && s.id || ""
                            }).html(a.join("")).css({
                                top: G(this).offset().top,
                                left: t
                            }).show().removeClass().addClass(l + " animated"),
                            G(".condition-table").on("click",
                            function() {
                                return ! 1
                            }),
                            G("#soul-condition" + C + " button[data-type]").on("click",
                            function() {
                                if ("add" === G(this).data("type")) {
                                    var e, i = G("#soul-condition" + C).data("id"),
                                    t = G("#soul-condition" + C).data("head"),
                                    l = G("#soul-condition" + C).find("tr:eq(0)");
                                    e = i ? {
                                        head: t,
                                        prefix: "and",
                                        field: n,
                                        mode: "condition",
                                        type: "eq",
                                        value: "",
                                        groupId: i
                                    }: {
                                        head: t,
                                        prefix: "and",
                                        mode: "group",
                                        field: n,
                                        children: [{
                                            id: k.getDifId(),
                                            prefix: "and",
                                            field: n,
                                            mode: "condition",
                                            type: l.find("select").val(),
                                            value: l.find(".value").val()
                                        },
                                        {
                                            id: k.getDifId(),
                                            prefix: "and",
                                            field: n,
                                            mode: "condition",
                                            type: "eq",
                                            value: ""
                                        }]
                                    },
                                    k.updateWhere(g, e),
                                    i || (G("#soul-condition" + C).data("id", e.id), l.data("id", e.children[0].id));
                                    var a = '<tr data-id="' + (i ? e.id: e.children[1].id) + '"><td>   <div>      <input type="checkbox" name="switch" lay-filter="soul-coondition-switch" lay-skin="switch" lay-text="与|或" checked>   </div></td><td style="width: 110px;"><div class="layui-block">' + c + '</div></td><td style="width: 110px;"><div class="layui-block"><input class="layui-input value" placeholder="值" /></div></td><td><i class="layui-icon layui-icon-delete del" style="font-size: 23px; color: #FF5722; cursor: pointer"></i></td></tr>';
                                    G("#soul-condition" + C + ">table>tbody").append(a),
                                    G("#soul-condition" + C).find(".del:last").on("click",
                                    function() {
                                        b(this)
                                    }),
                                    G("#soul-condition" + C + " input.value:last").on("input",
                                    function() {
                                        m(G(this).parents("tr:eq(0)"))
                                    })
                                } else "search" === G(this).data("type") && k.soulReload(g);
                                return H.render("select", "orm"),
                                H.render("checkbox", "orm"),
                                !1
                            }),
                            G("#soul-condition" + C + " input.value").on("input",
                            function() {
                                m(G(this).parents("tr:eq(0)"))
                            }),
                            H.on("select(conditionChange)",
                            function(e) {
                                "null" === e.value || "notNull" === e.value ? G(this).parents("tr").find("input.value").hide() : G(this).parents("tr").find("input.value").show(),
                                m(G(e.elem).parents("tr:eq(0)"))
                            }),
                            H.on("switch(soul-coondition-switch)",
                            function(e) {
                                m(G(this).parents("tr:eq(0)"))
                            }),
                            G("#soul-condition" + C + " .del").on("click",
                            function() {
                                b(this)
                            })
                        }
                        H.render("select", "orm"),
                        H.render("checkbox", "orm")
                    }),
                    G("#soul-columns" + C + ", #soul-dropList" + C).on("mouseover",
                    function(e) {
                        e.stopPropagation()
                    }),
                    G("#main-list" + C + " .soul-edit-condition").on("mouseover",
                    function(e) {
                        k.hideColumns(g),
                        k.hideDropList(g),
                        k.hideCondition(g),
                        e.stopPropagation()
                    }).on("click",
                    function() {
                        G("#main-list" + C).hide(),
                        k.showConditionBoard(g)
                    }),
                    G("#main-list" + C + " .soul-export").on("mouseover",
                    function(e) {
                        k.hideColumns(g),
                        k.hideDropList(g),
                        k.hideCondition(g),
                        e.stopPropagation()
                    }).on("click",
                    function() {
                        G("#main-list" + C).hide(),
                        k.export(ee[g.id])
                    }),
                    G("#main-list" + C + " .soul-clear-cache").on("mouseover",
                    function(e) {
                        k.hideColumns(g),
                        k.hideDropList(g),
                        k.hideCondition(g),
                        e.stopPropagation()
                    }).on("click",
                    function() {
                        G("#main-list" + C).hide(),
                        localStorage.removeItem(location.pathname + location.hash + g.id),
                        layer.msg("清除成功！请刷新页面！", {
                            icon: 1,
                            time: 1e3
                        })
                    }),
                    G("#main-list" + C).on("mouseover",
                    function(e) {
                        var i = e.pageX,
                        t = e.pageY,
                        l = G(this),
                        a = l.offset().top,
                        o = a + l.height(),
                        d = l.offset().left,
                        n = d + l.width();
                        i <= d || n <= i || t <= a || o <= t || (k.hideColumns(g), k.hideDropList(g), k.hideCondition(g))
                    })
                } else {
                    for (y = {},
                    x = 0; x < w.length; x++)"checkbox" !== w[x].type && w[x].field && w[x].filter && w[x].filter.type && (w[x].filter.field ? y[w[x].filter.field] = w[x].filter.type: y[w[x].field] = w[x].filter.type);
                    2 !== JSON.stringify(y).length && (g.where.tableFilterType = JSON.stringify(y))
                }
                if (0 === G("#soulDropList" + C).length && G("body").append('<div id="soulDropList' + C + '" style="display: none"></div>'), 0 < i.find(".soul-table-filter").length) {
                    var m = [];
                    if (i.find(".soul-table-filter").each(function(e, i) {
                        G(this).data("column") && m.push(G(this).data("column"))
                    }), -1 !== o.indexOf("data")) if (void 0 !== g.url && g.page) {
                        var b = JSON.parse(JSON.stringify(g.where)),
                        L = g.url;
                        b.columns = JSON.stringify(m),
                        G.ajax({
                            url: L,
                            data: b,
                            dataType: "json",
                            method: "GET",
                            headers: g.headers || {},
                            contentType: g.contentType,
                            success: function(e) {
                                var i = [];
                                for (var t in e) {
                                	console.log(t)
                                	console.log(e)
                                    var l = e[t];
                                	console.log(l)
                                    if (1 === l.length && "" === l[0] || 0 === l.length) i.push("<ul class='" + t + "DropList' data-value='" + t + "'><li style='color: gray;line-height: 25px;padding-left: 20px;'>(无数据)</li></ul>");
                                    else {
                                        var a = [];
                                        a.push("<ul class='" + t + "DropList' data-value='" + t + "'>");
                                        var o = w;
                                        for (u = 0; u < o.length; u++) if (o[u].field === t) {
                                            if (o[u].filter.split) {
                                                var d = [];
                                                for (x = 0; x < l.length; x++) for (var n = l[x].split(o[u].filter.split), s = 0; s < n.length; s++) - 1 === d.indexOf(n[s]) && d.push(n[s]);
                                                l = d
                                            }
                                            for (l.sort(function(e, i) {
                                                return isNaN(e) || isNaN(i) ? String(e) >= String(i) : Number(e) - Number(i)
                                            }), x = 0; x < l.length; x++) if (l[x]) {
                                                var r = {};
                                                r[t] = l[x],
                                                a.push('<li data-value="' + String(l[x]).toLowerCase() + '"><input type="checkbox" value="' + l[x] + '" title="' + ((o[u].templet && "function" == typeof o[u].templet ? o[u].templet.call(this, r) : l[x]) + "").replace(/\"|\'/g, "'") + '" lay-skin="primary" lay-filter="soulDropList' + C + '"></li>')
                                            }
                                            break
                                        }
                                        a.push("</ul>"),
                                        i.push(a.join(""))
                                    }
                                }
                                G("#soulDropList" + C).html(i.join(""))
                            },
                            error: function() {}
                        })
                    } else {
                        var S = j[g.id],
                        D = {};
                        for (x = 0; x < S.length; x++) for (u = 0; u < m.length; u++) {
                            var N = void 0 === S[x][m[u]] ? "": S[x][m[u]];
                            D[m[u]] ? -1 === D[m[u]].indexOf(N) && D[m[u]].push(N) : D[m[u]] = [N]
                        }
                        var O = w,
                        T = [];
                        for (u = 0; u < O.length; u++) {
                            var I = O[u].field,
                            F = D[I];
                            if (!F || 1 === F.length && "" === F[0]) T.push("<ul class='" + I + "DropList' data-value='" + I + "'><li style='color: gray;line-height: 25px;padding-left: 20px;'>(无数据)</li></ul>");
                            else {
                                if (O[u].filter && O[u].filter.split) {
                                    var B = [];
                                    for (x = 0; x < F.length; x++) for (var A = String(F[x]).split(O[u].filter.split), P = 0; P < A.length; P++) - 1 === B.indexOf(A[P]) && B.push(A[P]);
                                    F = B
                                }
                                F.sort(function(e, i) {
                                    return isNaN(e) || isNaN(i) ? String(e) >= String(i) : Number(e) - Number(i)
                                });
                                var W = [];
                                for (W.push("<ul class='" + I + "DropList' data-value='" + I + "'>"), x = 0; x < F.length; x++) if (F[x]) {
                                    var _ = {};
                                    _[I] = F[x],
                                    W.push('<li data-value="' + String(F[x]).toLowerCase() + '"><input type="checkbox" value="' + F[x] + '" title="' + ((O[u].templet && "function" == typeof O[u].templet ? O[u].templet.call(this, _) : F[x]) + "").replace(/\"|\'/g, "'") + '" lay-skin="primary" lay-filter="soulDropList' + C + '"></li>')
                                }
                                W.push("</ul>"),
                                T.push(W.join(""))
                            }
                        }
                        G("#soulDropList" + C).html(T.join(""))
                    } else k.bindFilterClick(g)
                }
                this.bindFilterClick(g)
            }
        },
        showConditionBoard: function(p) {
            var a, y, v = this,
            m = p.id,
            e = Q[p.id] || {},
            o = e.tableFilterType ? JSON.parse(e.tableFilterType) : {},
            i = e.filterSos ? JSON.parse(e.filterSos) : [],
            d = [],
            n = {},
            t = v.getCompleteCols(p.cols);
            for (y = 0; y < t.length; y++) t[y].field && t[y].filter && (a || (a = t[y]), n[t[y].field] = t[y].title);
            for (d.push('<div class="soul-edit-out">'), d.push('<div class="layui-form" lay-filter="soul-edit-out">'), d.push('<div><a class="layui-btn layui-btn-sm" data-type="addOne"><i class="layui-icon layui-icon-add-1"></i> 添加条件</a><a class="layui-btn layui-btn-sm" data-type="addGroup"><i class="layui-icon layui-icon-add-circle" ></i> 添加分组</a><a class="layui-btn layui-btn-sm" data-type="search" style="float: right"><i class="layui-icon layui-icon-search"></i> 查询</a><span style="float: right"><input type="checkbox" lay-filter="out_auto" class="out_auto" title="实时更新"></span></div>'), d.push("<hr>"), d.push("<ul>"), y = 0; y < i.length; y++) u(i[y], d, n, 0 === y, y === i.length - 1);
            function u(e, i, t, l, a) {
                var o = e.id,
                d = e.field,
                n = e.mode,
                s = e.type,
                r = "or" === e.prefix;
                switch (i.push('<li data-id="' + o + '" data-field="' + d + '" ' + (a ? 'class="last"': "") + ' data-mode="' + n + '" data-type="' + s + '" data-value="' + (void 0 === e.value ? "": e.value) + '" >'), i.push('<div><table><tbody><tr><td data-type="top"></td></tr><tr><td data-type="bottom"></td></tr></tbody></table></div>'), i.push('<div><input type="checkbox" name="switch" lay-filter="soul-edit-switch" lay-skin="switch" lay-text="与|或" ' + (r ? "": "checked") + "></div>"), n) {
                case "in":
                    i.push('<div class="layui-firebrick item-field">' + t[d] + "</div>"),
                    i.push('<div class="layui-deeppink item-type" >筛选数据</div>'),
                    i.push('<div class="layui-blueviolet item-value">共' + (e.values ? e.values.length: 0) + "条数据</div>"),
                    i.push('<div class="layui-red delete-item"><i class="layui-icon layui-icon-close-fill"></i></div>');
                    break;
                case "date":
                    i.push('<div class="layui-firebrick item-field">' + t[d] + "</div>"),
                    i.push('<div class="layui-deeppink item-type">选择日期</div>'),
                    i.push('<div class="layui-blueviolet item-value">' + ("specific" === e.type ? e.value || "请选择": b[e.type]) + "</div>"),
                    i.push('<div class="layui-red delete-item"><i class="layui-icon layui-icon-close-fill"></i></div>');
                    break;
                case "condition":
                    i.push('<div class="layui-firebrick item-field">' + t[d] + "</div>"),
                    i.push('<div class="layui-deeppink item-type">' + z[e.type] + "</div>"),
                    "null" !== s && "notNull" !== s && i.push('<div class="layui-blueviolet item-value">' + (void 0 === e.value || "" === e.value ? "请输入.....": e.value) + "</div>"),
                    i.push('<div class="layui-red delete-item"><i class="layui-icon layui-icon-close-fill"></i></div>');
                    break;
                case "group":
                    if (i.push('<div class="layui-firebrick">分组</div>'), i.push('<div><a class="layui-btn layui-btn-xs" data-type="addOne"><i class="layui-icon layui-icon-add-1"></i> 添加条件</a><a class="layui-btn layui-btn-xs" data-type="addGroup"><i class="layui-icon layui-icon-add-circle"></i> 添加分组</a></div>'), i.push('<div class="layui-red delete-item"><i class="layui-icon layui-icon-close-fill"></i></div>'), i.push('<ul class="group ' + (a ? "": "line") + '">'), e.children) for (y = 0; y < e.children.length; y++) u(e.children[y], i, t, 0 === y, y === e.children.length - 1);
                    i.push("</ul>")
                }
                i.push("</li>")
            }
            function s(e) {
                v.hideDropList(p),
                v.hideCondition(p),
                v.hideColumns(p),
                v.hideBfPrefix(p),
                v.hideBfType(p);
                var i = G(e).offset().top + G(e).outerHeight(),
                t = G(e).offset().left;
                G("#soul-bf-column" + m).find("li.soul-bf-selected").removeClass("soul-bf-selected"),
                G("#soul-bf-column" + m).data("field", G(e).parent().data("field")).data("id", G(e).parent().data("id")).data("mode", G(e).parent().data("mode")).data("group", G(e).parents("li:eq(2)").data("id") || "").data("refresh", G(".soul-edit-out .out_auto").prop("checked")).show().css({
                    top: i,
                    left: t
                }).removeClass().addClass("fadeInUp animated").find('li[data-field="' + G(e).parent().data("field") + '"]').addClass("soul-bf-selected")
            }
            function r(e) {
                v.hideDropList(p),
                v.hideCondition(p),
                v.hideColumns(p),
                v.hideBfColumn(p),
                v.hideBfPrefix(p);
                var i = G(e).offset().top + G(e).outerHeight(),
                t = G(e).offset().left,
                l = G(e).parent().data("field");
                switch (o[l] && 0 === o[l].indexOf("date") ? G("#soul-bf-type" + m + " li[data-mode=date]").show() : G("#soul-bf-type" + m + " li[data-mode=date]").hide(), G("#soul-bf-type" + m).find("li.soul-bf-selected").removeClass("soul-bf-selected"), G(e).parent().data("mode")) {
                case "in":
                    G("#soul-bf-type" + m).find('li[data-mode="in"]').addClass("soul-bf-selected");
                    break;
                case "date":
                    G("#soul-bf-type" + m).find('li[data-mode="date"]').addClass("soul-bf-selected");
                case "condition":
                    G("#soul-bf-type" + m).find('li[data-value="' + G(e).parent().data("type") + '"]').addClass("soul-bf-selected")
                }
                G("#soul-bf-type" + m).data("type", G(e).parent().data("type")).data("mode", G(e).parent().data("mode")).data("id", G(e).parent().data("id")).data("group", G(e).parents("li:eq(2)").data("id") || "").data("refresh", G(".soul-edit-out .out_auto").prop("checked")).show().css({
                    top: i,
                    left: t
                }).removeClass().addClass("fadeInUp animated")
            }
            function c(i) {
                v.hideColumns(p),
                v.hideBfType(p),
                v.hideBfPrefix(p),
                v.hideBfColumn(p);
                var e = G(i).offset().left,
                t = G(i).parent().data("mode"),
                l = G(i).parent().data("field"),
                a = G(i).parent().data("id"),
                o = G(i).parent().data("head"),
                d = G(i).parent().data("prefix"),
                n = G(i).parent().data("value"),
                s = G(".soul-edit-out .out_auto").prop("checked"),
                r = Q[p.id] || {},
                u = r.filterSos ? JSON.parse(r.filterSos) : [];
                switch (t) {
                case "in":
                    if (v.hideCondition(p), q && clearTimeout(q), G("#soul-dropList" + m + ">.filter-search>input").val(""), G("#soul-dropList" + m).show(), G("#soulDropList" + m).find("." + l + "DropList li input[type=checkbox]:checked").prop("checked", !1), (h = v.getFilterSoById(u, a)).values) for (y = 0; y < h.values.length; y++) G("#soulDropList" + m).find("." + l + 'DropList li input[type=checkbox][value="' + h.values[y] + '"]').prop("checked", !0);
                    G("#soul-dropList" + m + ">ul").data("id", a).data("head", o).data("refresh", s).data("prefix", d).html(G("#soulDropList" + m).find("." + l + "DropList li").clone()),
                    H.render("checkbox", "orm"),
                    f = G(i).offset().top + G(i).outerHeight(),
                    G("#soul-dropList" + m).css({
                        top: f,
                        left: e
                    }).show().removeClass().addClass("fadeInUp animated"),
                    setTimeout(function() {
                        G("#soul-dropList" + m + ">.filter-search>input").focus()
                    },
                    1);
                    var c = !0;
                    H.on("checkbox(soulDropList" + m + ")",
                    function(e) {
                        c = !1,
                        v.updateDropList(p, l)
                    }),
                    G("#soul-dropList" + m + ">ul>li[data-value]").on("click",
                    function() {
                        c && G(this).find("div.layui-form-checkbox").trigger("click"),
                        c = !0
                    });
                    break;
                case "date":
                    v.hideDropList(p),
                    J && clearTimeout(J);
                    var h = v.getFilterSoById(u, a),
                    f = G(i).offset().top + G(i).height();
                    v.showDate(p, l, h, "fadeInUp", f, e, "down", s);
                    break;
                case "condition":
                    G(i).hide(),
                    G(i).after('<div><input class="layui-input tempValue" value="" /></div>'),
                    G(i).next().children().val(n).select().on("keydown",
                    function(e) {
                        13 === e.keyCode && G(this).blur()
                    }).on("blur",
                    function() {
                        var e = G(this).val();
                        G(i).html(void 0 === e || "" === e ? "请输入...": e),
                        G(i).show(),
                        G(this).parent().remove(),
                        e !== n && (G(i).parent().data("value", e), v.updateWhere(p, {
                            id: a,
                            value: e
                        }), s && v.soulReload(p))
                    })
                }
            }
            d.push("</ul>"),
            d.push("</div>"),
            d.push("</div>"),
            layer.open({
                title: "编辑条件",
                type: 1,
                offset: "auto",
                area: ["480px", "480px"],
                content: d.join("")
            }),
            H.render(null, "soul-edit-out"),
            H.on("checkbox(out_auto)",
            function(e) {
                e.elem.checked && v.soulReload(p)
            }),
            H.on("switch(soul-edit-switch)",
            function(e) {
                var i, t, l, a;
                t = (i = e).elem.checked ? "and": "or",
                l = G(i.elem).parents("li:eq(0)").data("id"),
                a = G(".soul-edit-out .out_auto").prop("checked"),
                G(i.elem).parents("li:eq(0)").data("prefix", t),
                v.updateWhere(p, {
                    id: l,
                    prefix: t
                }),
                a && v.soulReload(p)
            }),
            G(".soul-edit-out .item-field").on("click",
            function(e) {
                e.stopPropagation(),
                s(this)
            }),
            G(".soul-edit-out .item-type").on("click",
            function(e) {
            	coonsole.log(5)
                e.stopPropagation(),
                r(this)
            }),
            G(".soul-edit-out .item-value").on("click",
            function(e) {
            	coonsole.log(6)
                e.stopPropagation(),
                c(this)
            }),
            G(".soul-edit-out .delete-item").on("click",
            function() {
            	coonsole.log(7)
                var e = G(this).parent().data("id"),
                i = G(".soul-edit-out .out_auto").prop("checked");
                G(this).parent().remove(),
                v.updateWhere(p, {
                    id: e,
                    delete: !0
                }),
                i && v.soulReload(p)
            }),
            G(".soul-edit-out a[data-type]").on("click",
            function() {
                "search" === G(this).data("type") ? v.soulReload(p) : function e(i) {
                    var t = G(".soul-edit-out .out_auto").prop("checked");
                    d = [];
                    switch (G(i).data("type")) {
                    case "addOne":
                        var l = {
                            prefix: "and",
                            field: a.field,
                            mode: "condition",
                            type: "eq",
                            value: ""
                        };
                        G(i).parent().parent().data("id") && G.extend(l, {
                            groupId: G(i).parent().parent().data("id")
                        }),
                        v.updateWhere(p, l),
                        d.push('<li data-id="' + l.id + '" data-field="' + l.field + '" data-mode="' + l.mode + '" data-type="' + l.type + '" data-value="' + l.value + '" data-prefix="' + l.prefix + '" class="last">'),
                        d.push('<div><table><tbody><tr><td data-type="top"></td></tr><tr><td data-type="bottom"></td></tr></tbody></table></div>'),
                        d.push('<div><input type="checkbox" name="switch" lay-filter="soul-edit-switch" lay-skin="switch" lay-text="与|或" checked></div>'),
                        d.push('<div class="layui-firebrick item-field">' + n[l.field] + "</div>"),
                        d.push('<div class="layui-deeppink item-type">等于</div>'),
                        d.push('<div class="layui-blueviolet item-value">请输入.....22</div>'),
                        d.push('<div class="layui-red delete-item">1<i class="layui-icon layui-icon-close-fill"></i></div>'),
                        d.push("</li>");
                        break;
                    case "addGroup":
                        var l = {
                            prefix: "and",
                            mode: "group",
                            children: []
                        };
                        G(i).parent().parent().data("id") && G.extend(l, {
                            groupId: G(i).parent().parent().data("id")
                        }),
                        v.updateWhere(p, l),
                        d.push('<li data-id="' + l.id + '" class="last">'),
                        d.push('<div><table><tbody><tr><td data-type="top"></td></tr><tr><td data-type="bottom"></td></tr></tbody></table></div>'),
                        d.push('<div><input type="checkbox" name="switch" lay-filter="soul-edit-switch" lay-skin="switch" lay-text="与|或" checked></div>'),
                        d.push('<div class="layui-firebrick">分组</div>'),
                        d.push('<div><a class="layui-btn layui-btn-xs" data-type="addOne"><i class="layui-icon layui-icon-add-1"></i> 添加条件</a><a class="layui-btn layui-btn-xs" data-type="addGroup"><i class="layui-icon layui-icon-add-circle"></i> 添加分组</a></div>'),
                        d.push('<div class="layui-red delete-item"><i class="layui-icon layui-icon-close-fill"></i></div>'),
                        d.push('<ul class="group">'),
                        d.push("</ul>"),
                        d.push("</li>")
                    }
                    t && v.soulReload(p);
                    0 < G(i).parent().parent().children("ul").children("li").length && (G(i).parent().parent().children("ul").children("li:last").removeClass("last"), 0 < G(i).parent().parent().children("ul").children("li:last").children("ul.group").length && G(i).parent().parent().children("ul").children("li:last").children("ul.group").addClass("line"));
                    G(i).parent().parent().children("ul").append(d.join(""));
                    H.render("checkbox", "soul-edit-out");
                    "addGroup" === G(i).data("type") ? G(i).parent().parent().children("ul").children("li:last").find("a[data-type]").on("click",
                    function() {
                        e(this)
                    }) : (G(i).parent().parent().children("ul").children("li:last").find(".item-field").on("click",
                    function(e) {
                        e.stopPropagation(),
                        s(this)
                    }), G(i).parent().parent().children("ul").children("li:last").find(".item-type").on("click",
                    function(e) {
                        e.stopPropagation(),
                        r(this)
                    }), G(i).parent().parent().children("ul").children("li:last").find(".item-value").on("click",
                    function(e) {
                        e.stopPropagation(),
                        c(this)
                    }));
                    G(i).parent().parent().children("ul").children("li:last").children(".delete-item").on("click",
                    function() {
                        var e = G(this).parent().data("id"),
                        i = G(".soul-edit-out .out_auto").prop("checked");
                        G(this).parent().remove(),
                        v.updateWhere(p, {
                            id: e,
                            delete: !0
                        }),
                        i && v.soulReload(p)
                    })
                } (this)
            })
        },
        hideColumns: function(e, i) {
            var t = e.id;
            G("#soul-columns" + t).removeClass().addClass("fadeOutLeft animated"),
            R && clearTimeout(R),
            void 0 === i || i ? R = setTimeout(function(e) {
                G("#soul-columns" + t).hide()
            },
            500) : G("[id^=soul-columns]").hide()
        },
        hideDropList: function(e, i) {
            var t = e.id;
            G("#soul-dropList" + t).removeClass().addClass("fadeOutLeft animated"),
            q && clearTimeout(q),
            void 0 === i || i ? q = setTimeout(function(e) {
                G("#soul-dropList" + t).hide()
            },
            500) : G("[id^=soul-dropList]").hide()
        },
        hideCondition: function(e, i) {
            var t = e.id;
            G("#soul-condition" + t).removeClass().addClass("fadeOutLeft animated"),
            J && clearTimeout(J),
            void 0 === i || i ? J = setTimeout(function(e) {
                G("#soul-condition" + t).hide()
            },
            500) : G("[id^=soul-condition]").hide()
        },
        hideBfPrefix: function(e, i) {
            var t = e.id;
            G("#soul-bf-prefix" + t).removeClass().addClass("fadeOutDown animated"),
            l && clearTimeout(l),
            void 0 === i || i ? l = setTimeout(function() {
                G("#soul-bf-prefix" + t).hide()
            },
            500) : G("[id=soul-bf-prefix" + t + "]").hide()
        },
        hideBfColumn: function(e, i) {
            var t = e.id;
            G("#soul-bf-column" + t).removeClass().addClass("fadeOutDown animated"),
            l && clearTimeout(l),
            void 0 === i || i ? l = setTimeout(function() {
                G("#soul-bf-column" + t).hide()
            },
            500) : G("[id=soul-bf-column" + t + "]").hide()
        },
        hideBfType: function(e, i) {
            var t = e.id;
            G("#soul-bf-type" + t).removeClass().addClass("fadeOutDown animated"),
            a && clearTimeout(a),
            void 0 === i || i ? a = setTimeout(function() {
                G("#soul-bf-type" + t).hide()
            },
            500) : G("[id=soul-bf-type" + t + "]").hide()
        },
        bindFilterClick: function(l) {
            var a, o = this,
            e = G(l.elem),
            i = e.next().children(".layui-table-box").children(".layui-table-header").children("table"),
            t = e.next().children(".layui-table-box").children(".layui-table-fixed-l").children(".layui-table-header").children("table"),
            d = e.next().children(".layui-table-box").children(".layui-table-fixed-r").children(".layui-table-header").children("table"),
            n = l.id;
            function s(i) {
                var e, t;
                o.hideColumns(l, !1),
                o.hideDropList(l, !1),
                o.hideCondition(l, !1),
                G("[id^=main-list]").hide(),
                G("#main-list" + n).data({
                    field: i.data("column"),
                    split: i.data("split")
                }),
                G("#soul-columns" + n + " [type=checkbox]").attr("disabled", !1),
                G("#soul-columns" + n + " li[data-key=" + i.parents("th").data("key").split("-")[2] + "] [type=checkbox]").attr("disabled", !0),
                i.hasClass("layui-table-sort") ? G("#main-list" + n + " .soul-sort").show() : G("#main-list" + n + " .soul-sort").hide(),
                a && clearTimeout(a),
                t = i.offset().left + G("#main-list" + n).outerWidth() < document.body.clientWidth ? (e = i.offset().left + 10, "fadeInLeft") : (e = i.offset().left - G("#main-list" + n).outerWidth(), "fadeInRight"),
                G("#main-list" + n).data("type", l.where.tableFilterType && JSON.parse(l.where.tableFilterType)[i.data("column")] || "").hide().css({
                    top: i.offset().top + 10,
                    left: e
                }).show().removeClass().addClass(t + " animated"),
                G("#main-list" + n + " .soul-sort").on("click",
                function(e) {
                    i.siblings(".layui-table-sort").find(".layui-table-sort-" + G(this).data("value")).trigger("click"),
                    G("#main-list" + n).hide()
                }),
                H.render("checkbox", "orm")
            }
            i.find(".soul-table-filter").off("click").on("click",
            function(e) {
                e.stopPropagation(),
                s(G(this))
            }),
            t.find(".soul-table-filter").off("click").on("click",
            function(e) {
                e.stopPropagation(),
                s(G(this))
            }),
            d.find(".soul-table-filter").off("click").on("click",
            function(e) {
                e.stopPropagation(),
                s(G(this))
            }),
            G(document).on("click",
            function(e) {
                G("#main-list" + n).hide(),
                o.hideColumns(l, !1),
                o.hideDropList(l, !1),
                o.hideCondition(l, !1),
                o.hideBfPrefix(l, !1),
                o.hideBfColumn(l, !1),
                o.hideBfType(l, !1)
            }),
            G("#main-list" + n + ",#soul-columns" + n + ",#soul-dropList" + n + ",#soul-condition" + n).on("click",
            function(e) {
                G(this).find(".layui-form-selected").removeClass("layui-form-selected"),
                e.stopPropagation()
            }),
            o.renderBottomCondition(l),
            G(window).on("resize",
            function() {
                o.resize(l)
            });
            for (var r = Q[l.id] || {},
            u = JSON.parse(r.filterSos ? r.filterSos: "[]"), c = 0; c < u.length; c++) if (u[c].head) {
                var h = !1;
                switch (u[c].mode) {
                case "in":
                    u[c].values && 0 < u[c].values.length && (h = !0);
                    break;
                case "date":
                    "all" !== u[c].type && void 0 !== u[c].value && "" !== u[c].value && (h = !0);
                    break;
                case "group":
                    u[c].children && 0 < u[c].children.length && (h = !0)
                }
                i.find('thead>tr>th[data-field="' + u[c].field + '"] .soul-table-filter').attr("soul-filter", "" + h),
                t.find('thead>tr>th[data-field="' + u[c].field + '"] .soul-table-filter').attr("soul-filter", "" + h),
                d.find('thead>tr>th[data-field="' + u[c].field + '"] .soul-table-filter').attr("soul-filter", "" + h)
            }
        },
        resize: function(e) {
            var i = G(e.elem),
            t = i.next().children(".layui-table-box").children(".layui-table-main");
            if (V.resize(e.id), 0 < i.next().children(".soul-bottom-contion").length) {
                i.next().children(".soul-bottom-contion").children(".condition-items").css("width", i.next().children(".soul-bottom-contion").width() - i.next().children(".soul-bottom-contion").children(".editCondtion").outerWidth());
                var l = i.next().height() - i.next().children(".soul-bottom-contion").outerHeight();
                0 < i.next().children(".layui-table-tool").length && (l -= i.next().children(".layui-table-tool").outerHeight()),
                0 < i.next().children(".layui-table-total").length && (l -= i.next().children(".layui-table-total").outerHeight()),
                0 < i.next().children(".layui-table-page").length && (l -= i.next().children(".layui-table-page").outerHeight()),
                l -= i.next().children(".layui-table-box").children(".layui-table-header").outerHeight(),
                i.next().children(".layui-table-box").children(".layui-table-body").height(l);
                var a = l - this.getScrollWidth(t[0]),
                o = t.children("table").height();
                i.next().children(".layui-table-box").children(".layui-table-fixed").children(".layui-table-body").height(a <= o ? a: "auto")
            }
        },
        updateDropList: function(e, i) {
            G(e.elem);
            var t = e.id,
            l = G("#soul-dropList" + t + ">ul").data("id"),
            a = G("#soul-dropList" + t + ">ul input[type=checkbox]:checked"),
            o = [],
            d = G("#soul-dropList" + t + ">ul").data("head"),
            n = G("#soul-dropList" + t + ">ul").data("prefix"),
            s = G("#soul-dropList" + t + ">ul").data("refresh"),
            r = G("#soul-dropList" + t + ">ul").data("split");
            0 < a.length && a.each(function() {
                o.push(G(this).val())
            });
            var u = {
                id: l,
                head: d,
                prefix: n || "and",
                mode: "in",
                field: i,
                split: r,
                values: o
            };
            this.updateWhere(e, u),
            l || G("#soul-dropList" + t + ">ul").data("id", u.id),
            0 < G(".soul-edit-out").length && G('.soul-edit-out li[data-id="' + u.id + '"]>.item-value').html("共" + (u.values ? u.values.length: 0) + "条数据"),
            s && this.soulReload(e)
        },
        getFilterSoById: function(e, i) {
            for (var t = 0; t < e.length; t++) {
                if (e[t].id === i) return e[t];
                if ("group" === e[t].mode) for (var l = 0; l < e[t].children.length; l++) {
                    var a = this.getFilterSoById(e[t].children, i);
                    if (a) return a
                }
            }
            return null
        },
        updateWhere: function(e, i) {
            var a = this,
            t = Q[e.id] || {},
            l = JSON.parse(t.filterSos ? t.filterSos: "[]");
            console.log(l)
            if (i.id || i.groupId) for (var o = 0; o < l.length; o++) {
                if (i.delete && l[o].id === i.id) {
                    l.splice(o, 1);
                    break
                }
                if (d(l[o], i)) break
            } else("in" !== i.mode || i.values && 0 < i.values.length) && l.push(G.extend(i, {
                id: a.getDifId()
            }));
            function d(e, i) {
                var t = !1;
                if (e.id === i.id && (G.extend(e, i), t = !0), i.id || e.id !== i.groupId) {
                    if ("group" === e.mode) for (var l = 0; l < e.children.length; l++) {
                        if (i.delete && e.children[l].id === i.id) return e.children.splice(l, 1),
                        !0;
                        if (d(e.children[l], i)) return ! 0
                    }
                } else e.children.push(G.extend(i, {
                    id: a.getDifId()
                }));
                return t
            }
            t.filterSos = JSON.stringify(l),
            console.log(t.filterSos)
            e.where = t,
            Q[e.id] = t
        },
        soulReload: function(e, i) {
            var a = this,
            t = G(e.elem),
            /*l = t.next().children(".layui-table-box").children(".layui-table-main").scrollLeft();
            console.log(eval(Q.recruitTable.filterSos))
            console.log(l)*/
            l={
            	userName:"齐勇"
            }
            console.log(Q[e.id])
            if (E[e.id] = void 0 === i || i, void 0 !== e.url && e.page) t.data("scrollLeft", l),
            V.reload(e.id, {
                where: l || {},
                page: {
                    curr: 1
                }
            });
            else {
                var o = Q[e.id] || {},
                d = JSON.parse(o.filterSos ? o.filterSos: "[]"),
                n = o.tableFilterType ? JSON.parse(o.tableFilterType) : {},
                s = layer.load(2);
                if (e.page || (e.limit = 1e8), 0 < d.length) {
                    var r = [];
                    if (layui.each(j[e.id],
                    function(e, i) {
                        for (var t = !0,
                        l = 0; l < d.length; l++) t = a.handleFilterSo(d[l], i, n, t, 0 === l);
                        t && r.push(i)
                    }), e.page) V.reload(e.id, {
                        data: r,
                        initSort: e.initSort,
                        isSoulFrontFilter: !0,
                        page: {
                            curr: 1
                        }
                    });
                    else {
                        var u = e.url;
                        t.next().off("click"),
                        V.reload(e.id, {
                            url: "",
                            initSort: e.initSort,
                            isSoulFrontFilter: !0,
                            data: r
                        }).config.url = u
                    }
                    e.data = r
                } else e.page ? V.reload(e.id, {
                    data: j[e.id],
                    initSort: e.initSort,
                    isSoulFrontFilter: !0,
                    page: {
                        curr: 1
                    }
                }) : V.reload(e.id, {
                    data: j[e.id],
                    initSort: e.initSort,
                    isSoulFrontFilter: !0
                }),
                e.data = j[e.id];
                t.next().children(".layui-table-box").children(".layui-table-main").scrollLeft(l),
                layer.close(s)
            }
        },
        handleFilterSo: function(e, i, t, l, a) {
            var o, d, n, s = !a && "or" === e.prefix,
            r = e.field,
            u = e.value,
            c = !0;
            if (e.children && 0 < e.children.length) {
                for (var h = 0; h < e.children.length; h++) c = this.handleFilterSo(e.children[h], i, t, c, 0 === h);
                return s ? l || c: l && c
            }
            switch (e.mode) {
            case "in":
                if (! (e.values && 0 < e.values.length)) return l;
                if (e.split) {
                    var f = (i[r] + "").split(e.split),
                    p = !1;
                    for (h = 0; h < f.length; h++) - 1 !== e.values.indexOf(f[h]) && (p = !0);
                    c = p
                } else c = -1 !== e.values.indexOf(i[r] + "");
                break;
            case "condition":
                if ("null" !== e.type && "notNull" !== e.type && (void 0 === u || "" === u)) return l;
                switch (e.type) {
                case "eq":
                    c = isNaN(i[r]) || isNaN(u) ? i[r] === u: Number(i[r]) === Number(u);
                    break;
                case "ne":
                    c = isNaN(i[r]) || isNaN(u) ? i[r] !== u: Number(i[r]) !== Number(u);
                    break;
                case "gt":
                    c = isNaN(i[r]) || isNaN(u) ? i[r] > u: Number(i[r]) > Number(u);
                    break;
                case "ge":
                    c = isNaN(i[r]) || isNaN(u) ? i[r] >= u: Number(i[r]) >= Number(u);
                    break;
                case "lt":
                    c = isNaN(i[r]) || isNaN(u) ? i[r] < u: Number(i[r]) < Number(u);
                    break;
                case "le":
                    c = isNaN(i[r]) || isNaN(u) ? i[r] <= u: Number(i[r]) <= Number(u);
                    break;
                case "contain":
                    c = -1 !== (i[r] + "").indexOf(u);
                    break;
                case "notContain":
                    c = -1 === (i[r] + "").indexOf(u);
                    break;
                case "start":
                    c = 0 === (i[r] + "").indexOf(u);
                    break;
                case "end":
                    var y = (i[r] + "").length - (u + "").length;
                    c = 0 <= y && (i[r] + "").lastIndexOf(u) === y;
                    break;
                case "null":
                    c = void 0 === i[r] || "" === i[r] || null === i[r];
                    break;
                case "notNull":
                    c = void 0 !== i[r] && "" !== i[r] && null !== i[r]
                }
                break;
            case "date":
                var v = new Date(Date.parse(i[r].replace(/-/g, "/")));
                switch (e.type) {
                case "all":
                    c = !0;
                    break;
                case "yesterday":
                    c = i[r] && x(v, b() - 1, b() - 86400);
                    break;
                case "thisWeek":
                    c = i[r] && x(v, g(), g() + 604800 - 1);
                    break;
                case "lastWeek":
                    c = i[r] && x(v, g() - 604800, g() - 1);
                    break;
                case "thisMonth":
                    c = i[r] && x(v, new Date((new Date).setDate(1)).setHours(0, 0, 0, 0) / 1e3, (o = new Date, d = o.getMonth(), n = ++d, new Date(o.getFullYear(), n, 1) / 1e3 - 1));
                    break;
                case "thisYear":
                    c = i[r] && x(v, new Date((new Date).getFullYear(), 1, 1) / 1e3, new Date((new Date).getFullYear() + 1, 1, 1) / 1e3 - 1);
                    break;
                case "specific":
                    var m = v.getFullYear();
                    m += "-" + k(v.getMonth() + 1),
                    m += "-" + k(v.getDate()),
                    c = i[r] && m === u
                }
            }
            function b() {
                return (new Date).setHours(0, 0, 0, 0) / 1e3
            }
            function g() {
                var e = new Date,
                i = e.getDay() || 7;
                return new Date(e.setDate(e.getDate() - i + 1)).setHours(0, 0, 0, 0) / 1e3
            }
            function x(e, i, t) {
                return e.getTime() / 1e3 >= i && e.getTime() / 1e3 <= t
            }
            function k(e) {
                return (e += "").length <= 1 && (e = "0" + e),
                e
            }
            return s ? l || c: l && c
        },
        getDifId: function() {
            return i++
        },
        showDate: function(a, o, e, i, t, l, d, n) {
            var s = this,
            r = a.id,
            u = [],
            c = document.body.clientWidth;
            for (var h in u.push('<div class="' + o + 'Condition" data-value="' + o + '" style="padding: 5px;" >'), u.push('<div class="layui-row">'), b) u.push('<div class="layui-col-sm4"><input type="radio" name="datetime' + r + o + '" lay-filter="datetime' + r + '" value="' + h + '" title="' + b[h] + '"></div>');
            u.push("</div>"),
            u.push('<div><input type="radio" name="datetime' + r + o + '" lay-filter="datetime' + r + '"  value="specific" title="过滤具体日期"> <input type="hidden" class="specific_value"><div class="staticDate"></div></div></div>'),
            G("#soul-condition" + r).html(u.join(""));
            var f = y.toDateString(new Date, "yyyy-MM-dd");
            e ? (G("#soul-condition" + r).data({
                id: e.id,
                head: !0
            }), G("#soul-condition" + r + ">." + o + 'Condition [name^=datetime][value="' + e.type + '"]').prop("checked", !0), "specific" === e.type && (f = e.value)) : (G("#soul-condition" + r).data({
                id: "",
                head: !0
            }), G("#soul-condition" + r + ">." + o + 'Condition [name^=datetime][value="all"]').prop("checked", !0)),
            G("#soul-condition" + r + " .specific_value").val(f),
            p.render({
                elem: "#soul-condition" + r + " .staticDate",
                position: "static",
                calendar: !0,
                btns: ["now"],
                value: f,
                done: function(e) {
                    var i = G("#soul-condition" + r).data("id"),
                    t = G("#soul-condition" + r).data("head");
                    G("#soul-condition" + r + " .specific_value").val(e),
                    G("#soul-condition" + r + " [name^=datetime]:checked").prop("checked", !1),
                    G("#soul-condition" + r + " [name^=datetime][value=specific]").prop("checked", !0);
                    var l = {
                        id: i,
                        head: t,
                        prefix: "and",
                        mode: "date",
                        field: o,
                        type: "specific",
                        value: e
                    };
                    s.updateWhere(a, l),
                    i || G("#soul-condition" + r).data("id", l.id),
                    0 < G(".soul-edit-out").length && G('.soul-edit-out li[data-id="' + l.id + '"]').children(".item-value").html(l.value),
                    n && s.soulReload(a),
                    H.render("radio", "orm")
                }
            }),
            H.on("radio(datetime" + r + ")",
            function(e) {
                var i = G("#soul-condition" + r).data("id"),
                t = G("#soul-condition" + r).data("head"),
                l = {
                    id: i,
                    head: t,
                    prefix: "and",
                    mode: "date",
                    field: o,
                    type: e.value,
                    value: G("#soul-condition" + r + " .specific_value").val()
                };
                s.updateWhere(a, l),
                i || G("#soul-condition" + r).data("id", l.id),
                0 < G(".soul-edit-out").length && G('.soul-edit-out li[data-id="' + l.id + '"]').children(".item-value").html(b[l.type] || l.value),
                n && s.soulReload(a)
            }),
            H.render("radio", "orm"),
            "down" === d ? i = l + G("#soul-condition" + r).width() < c ? "fadeInLeft": (l = l - G("#main-list" + r).width() - G("#soul-condition" + r).width(), "fadeInRight") : t = t - G("#soul-condition" + r).outerHeight() - 10,
            G("#soul-condition" + r).css({
                top: t,
                left: l
            }).show().removeClass().addClass(i + " animated")
        },
        bottomConditionHtml: function(e, i, t, l) {
            var a = "or" === i.prefix,
            o = i.field;
            if ("group" !== i.mode) {
                switch (e.push('<div class="condition-item" data-field="' + o + '" data-id="' + i.id + '" data-mode="' + i.mode + '" data-type="' + i.type + '" data-value="' + (void 0 === i.value ? "": i.value) + '" data-prefix="' + (i.prefix || "and") + '">'), l || e.push('<div class="item-prefix layui-red">' + (a ? "或": "与") + "</div> "), e.push('<div class="item-field layui-firebrick">' + t[o] + "</div> "), e.push('<div class="item-type layui-deeppink">'), i.mode) {
                case "in":
                    e.push("筛选数据");
                    break;
                case "condition":
                    e.push(z[i.type]);
                    break;
                case "date":
                    e.push("选择日期");
                    break;
                default:
                    e.push("未知")
                }
                if (e.push("</div> "), "null" !== i.type && "notNull" !== i.type) {
                    switch (e.push('<div class="item-value layui-blueviolet ' + ("date" === i.mode && "specific" !== i.type) + '">'), i.mode) {
                    case "in":
                        e.push("共" + (i.values ? i.values.length: 0) + "条数据");
                        break;
                    case "date":
                        e.push("specific" === i.type ? i.value || "请选择": b[i.type]);
                        break;
                    case "condition":
                    default:
                        e.push(void 0 === i.value || "" === i.value ? "请输入...": i.value)
                    }
                    e.push("</div>")
                }
                e.push('<i class="condition-item-close soul-icon soul-icon-unfold" ></i>'),
                e.push("</div>")
            } else if (i.children && 0 < i.children.length) {
                e.push('<div class="condition-item" data-id="' + i.id + '" data-prefix="' + (i.prefix || "and") + '">'),
                l || e.push('<div class="item-prefix layui-red">' + (a ? "或": "与") + "</div> ");
                for (var d = 0; d < i.children.length; d++) this.bottomConditionHtml(e, i.children[d], t, 0 === d);
                e.push('<i class="condition-item-close soul-icon soul-icon-unfold" ></i>'),
                e.push("</div>")
            }
        },
        renderBottomCondition: function(f) {
            for (var p = this,
            e = Q[f.id] || {},
            y = e.filterSos ? JSON.parse(e.filterSos) : [], d = e.tableFilterType ? JSON.parse(e.tableFilterType) : {},
            i = G(f.elem), v = f.id, t = i.next().children(".soul-bottom-contion"), n = {},
            l = [], a = f.filter && f.filter.items || ["column", "data", "condition", "editCondition", "excel"], o = p.getCompleteCols(f.cols), s = 0; s < o.length; s++) o[s].field && o[s].filter && (n[o[s].field] = o[s].title);
            for (s = 0; s < y.length; s++) p.bottomConditionHtml(l, y[s], n, 0 === s);
            if (t.children(".condition-items").html(l.join("")), l = [], 0 === G("#soul-bf-prefix" + v).length && (l.push('<div id="soul-bf-prefix' + v + '" style="display: none;"><ul>'), l.push('<li data-value="and">与</li>'), l.push('<li data-value="or">或</li>'), l.push("</ul></div>")), 0 === G("#soul-bf-column" + v).length) {
                for (var r in l.push('<div id="soul-bf-column' + v + '" style="display: none;"><ul>'), n) l.push('<li data-field="' + r + '">' + n[r] + "</li>");
                l.push("</ul></div>")
            }
            if (0 === G("#soul-bf-type" + v).length) {
                if (l.push('<div id="soul-bf-type' + v + '" style="display: none;"><ul>'), -1 !== a.indexOf("data") && l.push('<li data-value="in" data-mode="in">筛选数据</li>'), -1 !== a.indexOf("condition")) for (var u in l.push('<li data-value="all" data-mode="date">选择日期</li>'), z) l.push('<li data-value="' + u + '" data-mode="condition">' + z[u] + "</li>");
                l.push("</ul></div>")
            }
            0 === G("#soul-bf-cond2-dropList" + v).length && l.push('<div id="soul-bf-cond2-dropList' + v + '" style="display: none;"><div class="filter-search"><input type="text" placeholder="关键字搜索" class="layui-input"></div><div class="check"><div class="multiOption" data-type="all"><i class="soul-icon">&#xe623;</i> 全选</div><div class="multiOption" data-type="none"><i class="soul-icon">&#xe63e;</i> 清空</div><div class="multiOption" data-type="reverse"><i class="soul-icon">&#xe614;</i>反选</div></div><ul></ul></div>'),
            G("body").append(l.join("")),
            t.find(".item-prefix").off("click").on("click",
            function(e) {
                e.stopPropagation(),
                G("#main-list" + v).hide(),
                p.hideDropList(f),
                p.hideCondition(f),
                p.hideColumns(f),
                p.hideBfColumn(f),
                p.hideBfType(f);
                var i = G(this).offset().top - G("#soul-bf-prefix" + v).outerHeight() - 10,
                t = G(this).offset().left;
                G("#soul-bf-prefix" + v).find("li.soul-bf-selected").removeClass("soul-bf-selected"),
                G("#soul-bf-prefix" + v).data("id", G(this).parent().data("id")).data("prefix", G(this).parent().data("prefix")).data("refresh", !0).show().css({
                    top: i,
                    left: t
                }).removeClass().addClass("fadeInUp animated").find('li[data-value="' + G(this).parent().data("prefix") + '"]').addClass("soul-bf-selected")
            }),
            t.find(".item-field").off("click").on("click",
            function(e) {
                e.stopPropagation(),
                G("#main-list" + v).hide(),
                p.hideDropList(f),
                p.hideCondition(f),
                p.hideColumns(f),
                p.hideBfPrefix(f),
                p.hideBfType(f);
                var i = G(this).offset().top - G("#soul-bf-column" + v).outerHeight() - 10,
                t = G(this).offset().left;
                G("#soul-bf-column" + v).find("li.soul-bf-selected").removeClass("soul-bf-selected"),
                G("#soul-bf-column" + v).data("field", G(this).parent().data("field")).data("id", G(this).parent().data("id")).data("mode", G(this).parent().data("mode")).data("group", G(this).parent().parent().data("id") || "").data("refresh", !0).show().css({
                    top: i,
                    left: t
                }).removeClass().addClass("fadeInUp animated").find('li[data-field="' + G(this).parent().data("field") + '"]').addClass("soul-bf-selected")
            }),
            t.find(".item-type").on("click",
            function(e) {
                e.stopPropagation(),
                G("#main-list" + v).hide(),
                p.hideDropList(f),
                p.hideCondition(f),
                p.hideColumns(f),
                p.hideBfColumn(f),
                p.hideBfPrefix(f);
                var i = G(this).offset().top - G("#soul-bf-type" + v).outerHeight() - 10,
                t = G(this).offset().left,
                l = G(this).parent().data("field");
                switch (d[l] && 0 === d[l].indexOf("date") ? G("#soul-bf-type" + v + " li[data-mode=date]").show() : G("#soul-bf-type" + v + " li[data-mode=date]").hide(), G("#soul-bf-type" + v).find("li.soul-bf-selected").removeClass("soul-bf-selected"), G(this).parent().data("mode")) {
                case "in":
                    G("#soul-bf-type" + v).find('li[data-mode="in"]').addClass("soul-bf-selected");
                    break;
                case "date":
                    G("#soul-bf-type" + v).find('li[data-mode="date"]').addClass("soul-bf-selected");
                case "condition":
                    G("#soul-bf-type" + v).find('li[data-value="' + G(this).parent().data("type") + '"]').addClass("soul-bf-selected")
                }
                G("#soul-bf-type" + v).data("type", G(this).parent().data("type")).data("mode", G(this).parent().data("mode")).data("id", G(this).parent().data("id")).data("group", G(this).parent().parent().data("id") || "").data("refresh", !0).show().css({
                    top: i,
                    left: t
                }).removeClass().addClass("fadeInUp animated")
            }),
            t.find(".item-value").on("click",
            function(e) {
                e.stopPropagation(),
                G("#main-list" + v).hide(),
                p.hideColumns(f),
                p.hideBfType(f),
                p.hideBfPrefix(f),
                p.hideBfColumn(f);
                var i = G(this).offset().left,
                t = G(this).parent().data("mode"),
                l = G(this).parent().data("field"),
                a = G(this).parent().data("id"),
                o = G(this).parent().data("head"),
                d = G(this).parent().data("prefix");
                switch (t) {
                case "in":
                    p.hideCondition(f),
                    q && clearTimeout(q),
                    G("#soul-dropList" + v + ">.filter-search>input").val(""),
                    G("#soul-dropList" + v).show(),
                    G("#soulDropList" + v).find("." + l + "DropList li input[type=checkbox]:checked").prop("checked", !1);
                    for (var n = p.getFilterSoById(y, a), s = 0; s < n.values.length; s++) G("#soulDropList" + v).find("." + l + 'DropList li input[type=checkbox][value="' + n.values[s] + '"]').prop("checked", !0);
                    G("#soul-dropList" + v + ">ul").data("id", a).data("head", o).data("refresh", !0).data("prefix", d).html(G("#soulDropList" + v).find("." + l + "DropList li").clone()),
                    H.render("checkbox", "orm"),
                    u = G(this).offset().top - G("#soul-dropList" + v).outerHeight() - 10,
                    G("#soul-dropList" + v).css({
                        top: u,
                        left: i
                    }).show().removeClass().addClass("fadeInUp animated"),
                    setTimeout(function() {
                        G("#soul-dropList" + v + ">.filter-search>input").focus()
                    },
                    1);
                    var r = !0;
                    H.on("checkbox(soulDropList" + v + ")",
                    function(e) {
                        r = !1,
                        p.updateDropList(f, l)
                    }),
                    G("#soul-dropList" + v + ">ul>li[data-value]").on("click",
                    function() {
                        r && G(this).find("div.layui-form-checkbox").trigger("click"),
                        r = !0
                    });
                    break;
                case "date":
                    p.hideDropList(f),
                    J && clearTimeout(J);
                    n = p.getFilterSoById(y, a);
                    var u = G(this).offset().top - 10;
                    p.showDate(f, l, n, "fadeInUp", u, i, "up", !0);
                    break;
                default:
                    p.hideDropList(f),
                    J && clearTimeout(J);
                    var c = this,
                    h = G(this).parents(".condition-item:eq(0)").data("value");
                    G(c).hide(),
                    G(c).after('<div><input style="height: 25px;" class="layui-input tempValue" value="" /></div>'),
                    G(c).next().children().val(h).select().on("keydown",
                    function(e) {
                        13 === e.keyCode && G(this).blur()
                    }).on("blur",
                    function() {
                        var e = G(this).val();
                        G(c).html(void 0 === e || "" === e ? "请输入...": e),
                        G(c).show(),
                        G(this).parent().remove(),
                        e !== h && (p.updateWhere(f, {
                            id: a,
                            value: e
                        }), p.soulReload(f))
                    })
                }
            }),
            G("#soul-bf-prefix" + v + ">ul>li").off("click").on("click",
            function() {
                var e = G(this).parent().parent().data("id"),
                i = G(this).data("value"),
                t = G(this).parent().parent().data("prefix"),
                l = G(this).parent().parent().data("refresh");
                t !== i && (p.updateWhere(f, {
                    id: e,
                    prefix: i
                }), !0 === l && p.soulReload(f))
            }),
            G("#soul-bf-column" + v + ">ul>li").off("click").on("click",
            function() {
                var e = G(this).parent().parent().data("field"),
                i = G(this).data("field"),
                t = G(this).parent().parent().data("mode"),
                l = G(this).parent().parent().data("group"),
                a = G(this).parent().parent().data("refresh");
                if (e !== i) {
                    var o = {
                        id: G(this).parent().parent().data("id"),
                        field: i
                    };
                    "in" === t ? G.extend(o, {
                        values: []
                    }) : "date" !== t || p.startsWith(d[i], "date") || G.extend(o, {
                        mode: "in",
                        values: []
                    }),
                    l && p.updateWhere(f, {
                        id: l,
                        head: !1
                    }),
                    p.updateWhere(f, o),
                    0 < G(".soul-edit-out").length && (G('.soul-edit-out li[data-id="' + o.id + '"]').data(o).children(".item-field").html(n[i]), ("in" === o.mode || "date" === t && "date" !== o.mode) && (G('.soul-edit-out li[data-id="' + o.id + '"]').children(".item-type").html("筛选数据"), G('.soul-edit-out li[data-id="' + o.id + '"]').children(".item-value").html("共0条数据"))),
                    !0 === a && p.soulReload(f)
                }
            }),
            G("#soul-bf-type" + v + ">ul>li").off("click").on("click",
            function() {
                var e = G(this).data("value") + "",
                i = G(this).data("mode"),
                t = G(this).parent().parent().data("type"),
                l = G(this).parent().parent().data("mode"),
                a = G(this).parent().parent().data("group"),
                o = G(this).parent().parent().data("refresh");
                if (t !== e) {
                    var d = {
                        id: G(this).parent().parent().data("id"),
                        type: e,
                        mode: i
                    };
                    if (l !== i && G.extend(d, {
                        value: "",
                        values: []
                    }), a && "in" === i && p.updateWhere(f, {
                        id: a,
                        head: !1
                    }), p.updateWhere(f, d), 0 < G(".soul-edit-out").length) switch (G('.soul-edit-out li[data-id="' + d.id + '"]').data(d).children(".item-value").show(), G('.soul-edit-out li[data-id="' + d.id + '"]').data(d).children(".item-type").html(z[e] || ("in" === i ? "筛选数据": "选择日期")), i) {
                    case "in":
                        G('.soul-edit-out li[data-id="' + d.id + '"]').data(d).children(".item-value").html("共0条数据");
                        break;
                    case "date":
                        G('.soul-edit-out li[data-id="' + d.id + '"]').data(d).children(".item-value").html(b[e]);
                        break;
                    case "condition":
                        l !== i && G('.soul-edit-out li[data-id="' + d.id + '"]').data(d).children(".item-value").html("请输入..."),
                        G('.soul-edit-out li[data-id="' + d.id + '"]').data(d).children(".item-value")["null" === e || "notNull" === e ? "hide": "show"]()
                    } ! 0 === o && p.soulReload(f)
                }
            }),
            t.find(".condition-items .condition-item .condition-item-close").on("click",
            function() {
                p.updateWhere(f, {
                    id: G(this).parents(".condition-item:eq(0)").data("id"),
                    delete: !0
                }),
                p.soulReload(f)
            })
        },
        export: function(i, f) {
            "string" == typeof i && (i = ee[i]);
            var e = layer.msg("文件下载中", {
                icon: 16,
                time: -1,
                anim: -1,
                fixed: !1
            }),
            p = this.deepClone(i.cols),
            t = i.elem.next().find("style")[0],
            l = t.sheet || t.styleSheet || {},
            a = l.cssRules || l.rules;
            layui.each(a,
            function(e, i) {
                if (i.style.width) {
                    var t = i.selectorText.split("-");
                    p[t[3]][t[4]].width = parseInt(i.style.width)
                }
            });
            var o = JSON.parse(JSON.stringify(i.data || layui.table.cache[i.id])),
            d = {},
            n = {},
            s = [],
            r = {},
            u = G(i.elem),
            y = u.next().children(".layui-table-box").children(".layui-table-body").children("table"),
            v = void 0 === i.excel || !(!i.excel || void 0 !== i.excel.on && !i.excel.on) && i.excel;
            v = !0 === v ? {}: v || {};
            var c = (f = f || {}).filename ? "function" == typeof f.filename ? f.filename.call(this) : f.filename: v.filename ? "function" == typeof v.filename ? v.filename.call(this) : v.filename: "表格数据.xlsx",
            h = !0 === f.checked || !0 === v.checked,
            m = !0 === f.curPage || !0 === v.curPage,
            b = void 0 === f.columns ? v.columns: f.columns,
            g = void 0 === f.totalRow ? v.totalRow: f.totalRow,
            x = c.substring(c.lastIndexOf(".") + 1, c.length),
            k = v.add && v.add.top && Array.isArray(v.add.top.data) ? v.add.top.data.length + 1 : 1,
            C = v.add && v.add.bottom && Array.isArray(v.add.bottom.data) ? v.add.bottom.data.length: 0;
            if (h) o = V.checkStatus(i.id).data;
            else if (m) o = layui.table.cache[i.id];
            else if (i.url && i.page) {
                var w = !0,
                L = Z[i.id] ? Q[i.id] : ee[i.id].where;
                if (i.contentType && 0 == i.contentType.indexOf("application/json") && (L = JSON.stringify(L)), G.ajax({
                    url: i.url,
                    data: L,
                    dataType: "json",
                    method: i.method || "post",
                    async: !1,
                    cache: !1,
                    headers: i.headers || {},
                    contentType: i.contentType,
                    success: function(e) {
                        "function" == typeof i.parseData && (e = i.parseData(e) || e),
                        e[i.response.statusName] != i.response.statusCode ? layer.msg('返回的数据不符合规范，正确的成功状态码应为："' + i.response.statusName + '": ' + i.response.statusCode, {
                            icon: 2,
                            anim: 6
                        }) : o = e[i.response.dataName]
                    },
                    error: function(e) {
                        layer.msg("请求异常！", {
                            icon: 2,
                            anim: 6
                        }),
                        w = !1
                    }
                }), !w) return
            } else {
                var S = u.next().children(".layui-table-box").children(".layui-table-header").find('.layui-table-sort[lay-sort$="sc"]:eq(0)');
                if (0 < S.length) {
                    var D = S.parent().parent().data("field");
                    switch (S.attr("lay-sort")) {
                    case "asc":
                        o = layui.sort(o, D);
                        break;
                    case "desc":
                        o = layui.sort(o, D, !0)
                    }
                }
            }
            var N, O, T, I, F, B = [];
            for (N = 0; N < p.length; N++) for (O = 0; O < p[N].length; O++) if (!p[N][O].exportHandled) {
                if (1 < p[N][O].rowspan) for (s.push([M(O + 1) + (N + k), M(O + 1) + (N + parseInt(p[N][O].rowspan) + k - 1)]), (F = this.deepClone(p[N][O])).exportHandled = !0, T = N + 1; T < p.length;) p[T].splice(O, 0, F),
                T++;
                if (1 < p[N][O].colspan) {
                    for (s.push([M(O + 1) + (N + k), M(O + parseInt(p[N][O].colspan)) + (N + k)]), (F = this.deepClone(p[N][O])).exportHandled = !0, T = 1; T < p[N][O].colspan; T++) p[N].splice(O, 0, F);
                    O = O + p[N][O].colspan - 1
                }
            }
            var A = p[p.length - 1];
            if (!1 !== g && i.totalRow) {
                var P = {},
                W = {};
                for (N = 0; N < A.length; N++) A[N].totalRowText ? P["numbers" === A[N].type ? "LAY_TABLE_INDEX": A[N].field] = A[N].totalRowText: A[N].totalRow && (W["numbers" === A[N].type ? "LAY_TABLE_INDEX": A[N].field] = 0);
                if ("{}" !== JSON.stringify(W)) for (N = 0; N < o.length; N++) for (var _ in W) W[_] += Number(o[N][_]) || 0;
                o.push(Object.assign(P, W))
            }
            if (b && Array.isArray(b)) {
                var R = [];
                for (I = {},
                s = [], B[0] = {},
                N = 0; N < b.length; N++) for (O = 0; O < A.length; O++) if (A[O].field === b[N]) {
                    R.push(A[O]),
                    B[0]["numbers" === A[O].type ? "LAY_TABLE_INDEX": A[O].field] = A[O],
                    I["numbers" === A[O].type ? "LAY_TABLE_INDEX": A[O].field] = A[O].title;
                    break
                }
                A = R,
                o.splice(0, 0, I)
            } else for (N = 0; N < p.length; N++) {
                for (B[N] = {},
                I = {},
                O = 0; O < p[N].length; O++) B[N]["numbers" === p[N][O].type ? "LAY_TABLE_INDEX": p[p.length - 1][O].field] = p[N][O],
                I["numbers" === p[N][O].type ? "LAY_TABLE_INDEX": p[p.length - 1][O].field] = p[N][O].title;
                o.splice(N, 0, I)
            }
            if (v.add) {
                var q, J, H, E = v.add.top,
                j = v.add.bottom;
                if (E && Array.isArray(E.data) && 0 < E.data.length) {
                    for (N = 0; N < E.data.length; N++) {
                        for (I = {},
                        O = H = 0; O < (E.data[N].length > A.length ? E.data[N].length: A.length); O++) ! A[O].field && "numbers" !== A[O].type || A[O].hide ? H++:I[A[O] ? "numbers" === A[O].type ? "LAY_TABLE_INDEX": A[O].field: O + ""] = E.data[N][O - H] || "";
                        o.splice(N, 0, I)
                    }
                    if (Array.isArray(E.heights) && 0 < E.heights.length) for (N = 0; N < E.heights.length; N++) r[N] = E.heights[N];
                    if (Array.isArray(E.merge) && 0 < E.merge.length) for (N = 0; N < E.merge.length; N++) 2 === E.merge[N].length && (q = E.merge[N][0].split(","), J = E.merge[N][1].split(","), s.push([M(q[1]) + q[0], M(J[1]) + J[0]]))
                }
                if (j && Array.isArray(j.data) && 0 < j.data.length) {
                    for (N = 0; N < j.data.length; N++) {
                        for (I = {},
                        O = H = 0; O < (j.data[N].length > A.length ? j.data[N].length: A.length); O++) ! A[O].field && "numbers" !== A[O].type || A[O].hide ? H++:I[A[O] ? "numbers" === A[O].type ? "LAY_TABLE_INDEX": A[O].field: O + ""] = j.data[N][O - H] || "";
                        o.push(I)
                    }
                    if (Array.isArray(j.heights) && 0 < j.heights.length) for (N = 0; N < j.heights.length; N++) r[o.length - j.data.length + N] = j.heights[N];
                    if (Array.isArray(j.merge) && 0 < j.merge.length) for (N = 0; N < j.merge.length; N++) 2 === j.merge[N].length && (q = j.merge[N][0].split(","), J = j.merge[N][1].split(","), s.push([M(q[1]) + (o.length - j.data.length + parseInt(q[0])), M(J[1]) + (o.length - j.data.length + parseInt(J[0]))]))
                }
            }
            var Y = 0,
            z = {
                left: "left",
                center: "center",
                right: "right"
            },
            X = ["top", "bottom", "left", "right"];
            for (N = 0; N < A.length; N++) ! A[N].field && "numbers" !== A[N].type || A[N].hide || (A[N].width && (n[String.fromCharCode(64 + parseInt(++Y))] = A[N].width), d["numbers" === A[N].type ? "LAY_TABLE_INDEX": A[N].field] = function(e, i, t, l) {
                var a = "ffffff",
                o = "000000",
                d = "Calibri",
                n = 12,
                s = "s",
                r = l - (b ? 1 : p.length) - k + 1,
                u = {
                    top: {
                        style: "thin",
                        color: {
                            indexed: 64
                        }
                    },
                    bottom: {
                        style: "thin",
                        color: {
                            indexed: 64
                        }
                    },
                    left: {
                        style: "thin",
                        color: {
                            indexed: 64
                        }
                    },
                    right: {
                        style: "thin",
                        color: {
                            indexed: 64
                        }
                    }
                };
                if (v.border) for (O = 0; O < X.length; O++) v.border[X[O]] ? (u[X[O]].style = v.border[X[O]].style || u[X[O]].style, u[X[O]].color = U(v.border[X[O]].color) || u[X[O]].color) : (v.border.color || v.border.style) && (u[X[O]].style = v.border.style || u[X[O]].style, u[X[O]].color = U(v.border.color) || u[X[O]].color);
                if (l < k - 1 || l >= t.length - C) return {
                    v: i[e] || "",
                    s: {
                        alignment: {
                            horizontal: "center",
                            vertical: "center"
                        },
                        font: {
                            name: d,
                            sz: n,
                            color: {
                                rgb: o
                            }
                        },
                        fill: {
                            fgColor: {
                                rgb: a,
                                bgColor: {
                                    indexed: 64
                                }
                            }
                        },
                        border: u
                    },
                    t: s
                };
                if (r < 0) a = "C7C7C7",
                v.head && (a = v.head.bgColor || a, o = v.head.color || o, d = v.head.family || d, n = v.head.size || n),
                f.head && (a = f.head.bgColor || a, o = f.head.color || o, d = f.head.family || d, n = f.head.size || n);
                else {
                    if (v.font && (a = v.font.bgColor || a, o = v.font.color || o, d = v.font.family || d, n = v.font.size || n), f.font && (a = f.font.bgColor || a, o = f.font.color || o, d = f.font.family || d, n = f.head.size || n), f.border) for (O = 0; O < X.length; O++) f.border[X[O]] ? (u[X[O]].style = f.border[X[O]].style || u[X[O]].style, u[X[O]].color = U(f.border[X[O]].color) || u[X[O]].color) : (f.border.color || f.border.style) && (u[X[O]].style = f.border.style || u[X[O]].style, u[X[O]].color = U(f.border.color) || u[X[O]].color);
                    if (B[B.length - 1][e].excel) {
                        var c = "function" == typeof B[B.length - 1][e].excel ? B[B.length - 1][e].excel.call(this, i, r, t.length - p.length - k + 1 - C) : B[B.length - 1][e].excel;
                        if (c && (a = c.bgColor || a, o = c.color || o, d = c.family || d, n = c.size || n, s = c.cellType || s, c.border)) for (O = 0; O < X.length; O++) c.border[X[O]] ? (u[X[O]].style = c.border[X[O]].style || u[X[O]].style, u[X[O]].color = U(c.border[X[O]].color) || u[X[O]].color) : (c.border.color || c.border.style) && (u[X[O]].style = c.border.style || u[X[O]].style, u[X[O]].color = U(c.border.color) || u[X[O]].color)
                    }
                }
                function h(e) {
                    return null == e ? "": e
                }
                return {
                    v: 0 <= r && B[B.length - 1][e].templet ? "function" == typeof B[B.length - 1][e].templet ? 0 === G("<div>" + B[B.length - 1][e].templet(i) + "</div>").find(":input").length ? G("<div>" + B[B.length - 1][e].templet(i) + "</div>").text() : y.children("tbody").children("tr[data-index=" + r + "]").children('td[data-field="' + e + '"]').find(":input").val() || h(i[e]) : 0 === G("<div>" + $(G(B[B.length - 1][e].templet).html() || String(B[B.length - 1][e].templet)).render(i) + "</div>").find(":input").length ? G("<div>" + $(G(B[B.length - 1][e].templet).html() || String(B[B.length - 1][e].templet)).render(i) + "</div>").text() : y.children("tbody").children("tr[data-index=" + r + "]").children('td[data-field="' + e + '"]').find(":input").val() || h(i[e]) : 0 <= r && "LAY_TABLE_INDEX" === e ? r + 1 : h(i[e]),
                    s: {
                        alignment: {
                            horizontal: B[r < -1 ? l - k + 1 : B.length - 1][e].align ? z[B[r < -1 ? l - k + 1 : B.length - 1][e].align] : "top",
                            vertical: "center"
                        },
                        font: {
                            name: d,
                            sz: n,
                            color: {
                                rgb: o
                            }
                        },
                        fill: {
                            fgColor: {
                                rgb: a,
                                bgColor: {
                                    indexed: 64
                                }
                            }
                        },
                        border: u
                    },
                    t: s
                }
            });
            function U(e) {
                return e ? {
                    rgb: e
                }: e
            }
            function M(e) {
                for (var i = []; e;) {
                    var t = e % 26;
                    t || (t = 26, --e),
                    String.fromCodePoint ||
                    function(d) {
                        var i = function(e) {
                            for (var i = [], t = "", l = 0, a = arguments.length; l !== a; ++l) {
                                var o = +arguments[l];
                                if (! (o < 1114111 && o >>> 0 === o)) throw RangeError("Invalid code point: " + o);
                                16383 <= (o <= 65535 ? i.push(o) : (o -= 65536, i.push(55296 + (o >> 10), o % 1024 + 56320))) && (t += d.apply(null, i), i.length = 0)
                            }
                            return t + d.apply(null, i)
                        };
                        try {
                            Object.defineProperty(String, "fromCodePoint", {
                                value: i,
                                configurable: !0,
                                writable: !0
                            })
                        } catch(e) {
                            String.fromCodePoint = i
                        }
                    } (String.fromCharCode),
                    i.push(String.fromCodePoint(t + 64)),
                    String.fromCodePoint ||
                    function(d) {
                        var i = function(e) {
                            for (var i = [], t = "", l = 0, a = arguments.length; l !== a; ++l) {
                                var o = +arguments[l];
                                if (! (o < 1114111 && o >>> 0 === o)) throw RangeError("Invalid code point: " + o);
                                16383 <= (o <= 65535 ? i.push(o) : (o -= 65536, i.push(55296 + (o >> 10), o % 1024 + 56320))) && (t += d.apply(null, i), i.length = 0)
                            }
                            return t + d.apply(null, i)
                        };
                        try {
                            Object.defineProperty(String, "fromCodePoint", {
                                value: i,
                                configurable: !0,
                                writable: !0
                            })
                        } catch(e) {
                            String.fromCodePoint = i
                        }
                    } (String.fromCharCode),
                    e = ~~ (e / 26)
                }
                return i.reverse().join("")
            }
            K.exportExcel({
                sheet1: K.filterExportData(o, d)
            },
            c, x, {
                extend: {
                    "!cols": K.makeColConfig(n, 80),
                    "!merges": K.makeMergeConfig(s),
                    "!rows": K.makeRowConfig(r, 16)
                }
            }),
            layer.close(e)
        },
        startsWith: function(e, i) {
            var t = new RegExp("^" + i);
            return e && t.test(e)
        },
        deepClone: function(e) {
            var i = Array.isArray(e) ? [] : {};
            if (e && "object" == typeof e) for (var t in e) e.hasOwnProperty(t) && (i[t] = e && "object" == typeof e[t] ? this.deepClone(e[t]) : e[t]);
            return i
        },
        deepStringify: function(e) {
            var t = "[[JSON_FUN_PREFIX_",
            l = "_JSON_FUN_SUFFIX]]";
            return JSON.stringify(e,
            function(e, i) {
                return "function" == typeof i ? t + i.toString() + l: i
            })
        },
        getScrollWidth: function(e) {
            var i = 0;
            return e ? i = e.offsetWidth - e.clientWidth: ((e = document.createElement("div")).style.width = "100px", e.style.height = "100px", e.style.overflowY = "scroll", document.body.appendChild(e), i = e.offsetWidth - e.clientWidth, document.body.removeChild(e)),
            i
        },
        getCompleteCols: function(e) {
            var i, t, l, a, o = this.deepClone(e);
            for (i = 0; i < o.length; i++) for (t = 0; t < o[i].length; t++) if (!o[i][t].exportHandled) {
                if (1 < o[i][t].rowspan) for ((a = this.deepClone(o[i][t])).exportHandled = !0, l = i + 1; l < o.length;) o[l].splice(t, 0, a),
                l++;
                if (1 < o[i][t].colspan) {
                    for ((a = this.deepClone(o[i][t])).exportHandled = !0, l = 1; l < o[i][t].colspan; l++) o[i].splice(t, 0, a);
                    t = t + o[i][t].colspan - 1
                }
            }
            return o[o.length - 1]
        },
        cache: j
    })
});