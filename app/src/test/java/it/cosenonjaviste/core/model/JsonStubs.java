package it.cosenonjaviste.core.model;

import com.annimon.stream.Stream;

import static com.annimon.stream.Collectors.joining;

public class JsonStubs {
    private static final String SINGLE_POST = "{\n" +
            "            \"id\": 12831,\n" +
            "            \"type\": \"post\",\n" +
            "            \"slug\": \"gestione-di-una-form-con-il-data-binding-android\",\n" +
            "            \"url\": \"http:\\/\\/www.cosenonjaviste.it\\/gestione-di-una-form-con-il-data-binding-android\\/\",\n" +
            "            \"status\": \"publish\",\n" +
            "            \"title\": \"Gestione di una form con il Data Binding Android\",\n" +
            "            \"excerpt\": \"<p>Una delle novit\\u00e0 pi\\u00f9 interessanti per gli sviluppatori Android fra quelle presentate al Google I\\/O 2015 \\u00e8 senza dubbio il framework di Data Binding. Ne abbiamo gi\\u00e0 parlato in un altro post, adesso \\u00e8 arrivato il momento di provare questo framework in un esempio reale. Nel sito ufficiale e in vari blog a giro per la rete ci sono alcuni [&hellip;]<\\/p>\\n\",\n" +
            "            \"date\": \"2015-07-07 08:55:30\",\n" +
            "            \"modified\": \"2015-07-08 19:46:35\",\n" +
            "            \"author\": {\n" +
            "                \"id\": 2,\n" +
            "                \"slug\": \"fabio-collini\",\n" +
            "                \"name\": \"Fabio Collini\",\n" +
            "                \"first_name\": \"Fabio\",\n" +
            "                \"last_name\": \"Collini\",\n" +
            "                \"nickname\": \"fabio.collini\",\n" +
            "                \"url\": \"http:\\/\\/www.cosenonjaviste.it\\/?team=fabio-collini\",\n" +
            "                \"email\": \"fabio.collini@gmail.com\"\n" +
            "            },\n" +
            "            \"attachments\": [\n" +
            "                {\n" +
            "                    \"id\": 12852,\n" +
            "                    \"url\": \"http:\\/\\/www.cosenonjaviste.it\\/wp-content\\/uploads\\/2015\\/07\\/databinding_android.png\",\n" +
            "                    \"slug\": \"databinding_android\",\n" +
            "                    \"title\": \"databinding_android\",\n" +
            "                    \"description\": \"\",\n" +
            "                    \"caption\": \"\",\n" +
            "                    \"parent\": 12831,\n" +
            "                    \"mime_type\": \"image\\/png\",\n" +
            "                    \"images\": {\n" +
            "                        \"full\": {\n" +
            "                            \"url\": \"http:\\/\\/www.cosenonjaviste.it\\/wp-content\\/uploads\\/2015\\/07\\/databinding_android.png\",\n" +
            "                            \"width\": 360,\n" +
            "                            \"height\": 640\n" +
            "                        },\n" +
            "                        \"thumbnail\": {\n" +
            "                            \"url\": \"http:\\/\\/www.cosenonjaviste.it\\/wp-content\\/uploads\\/2015\\/07\\/databinding_android-150x150.png\",\n" +
            "                            \"width\": 150,\n" +
            "                            \"height\": 150\n" +
            "                        }\n" +
            "                    }\n" +
            "                }\n" +
            "            ],\n" +
            "            \"comment_count\": 2,\n" +
            "            \"comment_status\": \"open\"\n" +
            "        }";


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
        return getPostList(0, numberOfPost);
    }

    public static String getPostList(int firstPost, int numberOfPost) {
        String s = Stream.ofRange(0, numberOfPost)
                .map(i -> SINGLE_POST.replace("%TITLE%", "post title " + (firstPost + i)))
                .collect(joining(","));
        return String.format(POSTS, s);
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
