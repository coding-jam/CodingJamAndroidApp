package it.cosenonjaviste.stubs;

public class JsonStubs {
    private static final String SINGLE_POST = "{\n" +
            "id: 11213,\n" +
            "type: \"post\",\n" +
            "slug: \"decorator-patten-corretto-lambda-con-java-8\",\n" +
            "url: \"http://www.cosenonjaviste.it/decorator-patten-corretto-lambda-con-java-8/\",\n" +
            "status: \"publish\",\n" +
            "title: \"Decorator patten corretto lambda con Java 8\",\n" +
            "date: \"2014-05-29 10:30:36\",\n" +
            "modified: \"2014-08-11 07:35:33\",\n" +
            "categories: [\n" +
            "\t{\n" +
            "\tid: 6,\n" +
            "\tslug: \"java\",\n" +
            "\ttitle: \"Java\",\n" +
            "\tdescription: \"\",\n" +
            "\tparent: 0,\n" +
            "\tpost_count: 61\n" +
            "\t},\n" +
            "\t{\n" +
            "\tid: 545,\n" +
            "\tslug: \"java-8\",\n" +
            "\ttitle: \"Java 8\",\n" +
            "\tdescription: \"\",\n" +
            "\tparent: 6,\n" +
            "\tpost_count: 11\n" +
            "\t},\n" +
            "\t{\n" +
            "\tid: 418,\n" +
            "\tslug: \"tutorial-2\",\n" +
            "\ttitle: \"Tutorial\",\n" +
            "\tdescription: \"\",\n" +
            "\tparent: 0,\n" +
            "\tpost_count: 35\n" +
            "\t}\n" +
            "],\n" +
            "author: {\n" +
            "\tid: 8,\n" +
            "\tslug: \"giampaolo-trapasso\",\n" +
            "\tname: \"Giampaolo Trapasso\",\n" +
            "\tfirst_name: \"Giampaolo\",\n" +
            "\tlast_name: \"Trapasso\",\n" +
            "\tnickname: \"trapo\",\n" +
            "\turl: \"http://www.cosenonjaviste.it/?team=giampaolo-trapasso\",\n" +
            "\tdescription: \"aaaaa\"\n" +
            "},\n" +
            "comments: [ ],\n" +
            "attachments: [ ],\n" +
            "comment_count: 0,\n" +
            "comment_status: \"open\",\n" +
            "custom_fields: {}\n" +
            "}\n";


    private static final String POSTS = "{\n" +
            "status: \"ok\",\n" +
            "count: 6,\n" +
            "count_total: 183,\n" +
            "pages: 31,\n" +
            "posts: [\n" +
            "%s" +
            "]\n" +
            "}";

    public static String getPostList(int numberOfPost) {
        StringBuffer b = new StringBuffer();
        for (int i = 0; i < numberOfPost; i++) {
            if (i > 0) {
                b.append(',');
            }
            b.append(SINGLE_POST);
        }
        return String.format(POSTS, b.toString());
    }

    public static final String AUTHORS = "{\n" +
            "status: \"ok\",\n" +
            "count: 14,\n" +
            "authors: [\n" +
            "\t{\n" +
            "\t\tid: 13,\n" +
            "\t\tslug: \"sasa\",\n" +
            "\t\tname: \"Gianni\",\n" +
            "\t\tfirst_name: \"Gianni\",\n" +
            "\t\tlast_name: \"A\",\n" +
            "\t\tnickname: \"ga\",\n" +
            "\t\turl: \"\",\n" +
            "\t\tdescription: \"Sono laureato in Ingegneria Informatica...\",\n" +
            "\t\temail: \"aaaa@gmail.com\"\n" +
            "\t},\n" +
            "\t{\n" +
            "\t\tid: 7,\n" +
            "\t\tslug: \"gabriele\",\n" +
            "\t\tname: \"Gabriele\",\n" +
            "\t\tfirst_name: \"Gabriele\",\n" +
            "\t\tlast_name: \"Bia\",\n" +
            "\t\tnickname: \"gabriele\",\n" +
            "\t\turl: \"\",\n" +
            "\t\tdescription: \"Sviluppo da circa tre anni in Java e da quasi....\",\n" +
            "\t\temail: \"bbb@gmail.com\"\n" +
            "\t}\n" +
            "]\n" +
            "}";

    public static final String CATEGORIES = "{\n" +
            "status: \"ok\",\n" +
            "count: 3,\n" +
            "categories: [\n" +
            "{\n" +
            "id: 602,\n" +
            "slug: \"agile-2\",\n" +
            "title: \"Agile\",\n" +
            "description: \"\",\n" +
            "parent: 0,\n" +
            "post_count: 4\n" +
            "},\n" +
            "{\n" +
            "id: 16,\n" +
            "slug: \"ajax\",\n" +
            "title: \"AJAX\",\n" +
            "description: \"\",\n" +
            "parent: 0,\n" +
            "post_count: 9\n" +
            "},\n" +
            "{\n" +
            "id: 595,\n" +
            "slug: \"alfresco-2\",\n" +
            "title: \"Alfresco\",\n" +
            "description: \"\",\n" +
            "parent: 0,\n" +
            "post_count: 4\n" +
            "}" +
            "]" +
            "}";
}
