
$(function() {

    course_list.initial();
});

var course_list = {
    initial: function initial() {
        this.bindChangeSearchParam();
    },

    bindChangeSearchParam : function bindChangeSearchParam() {
        $("#question-filter dl dd span").click(function () {
            if ($(this).hasClass("label"))return false;

            var genrateParamOld = course_list.genrateParamOld();

            genrateParamOld.knowledgePointId = $(this).data("id");
            course_list.redirectUrl(genrateParamOld);


        });

        $(".pagination li a").click(function(){
            if ($(this).hasClass("label"))return false;
            var page = $(this).data("id");
            if(page==null||page=="")return false;
            var genrateParamOld = course_list.genrateParamOld();
            genrateParamOld.page = page;
            course_list.redirectUrl(genrateParamOld);

        });
    },

    genrateParamOld :function genrateParamOld(){
        var knowledgePointId = $("#question-filter-field dd .label").data("id");
        var page = 1;

        var data = new Object();
        data.knowledgePointId= knowledgePointId;
        data.page = page;

        return data;
    },

    redirectUrl : function(newparam) {
        var paramurl = newparam.knowledgePointId;
        paramurl = paramurl + "-" + newparam.page;
        paramurl = paramurl;

        var isAdmin = $(".isAdmin").val();
        if(isAdmin == 'true'){
            document.location.href = document.getElementsByTagName('base')[0].href
                                     + 'admin/course-list-' + paramurl;
        }else{
            document.location.href = document.getElementsByTagName('base')[0].href
                                     + 'student/course-list-' + paramurl;
        }
    },
}