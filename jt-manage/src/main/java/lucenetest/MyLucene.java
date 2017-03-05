package lucenetest;

/**
 * 将数据转化为文档对象Document
 *
 * Created by tarena on 2016/11/16.
 */
//public class MyLucene {
//    public static void main(String[] args) throws IOException{
//        Document document=new Document();
//        document.add(new LongField("id",562379L, Store.YES));
//        document.add(new TextField("title","三星 W999 黑色 电信3G手机 双卡双待双通",Store.YES));
//        document.add(new StringField("sellPoint","下单送12000毫安移动电源！双3.5英寸魔焕炫屏，以非凡视野纵观天下时局，尊崇翻盖设计，张弛中，尽显从容气度！",Store.YES));
//        document.add(new LongField("price",4299000L,Store.YES));
//        document.add(new LongField("num",99999,Store.YES));
//        document.add(new StringField("image","http://image.jt.com/jd/d2ac340e728d4c6181e763e772a9944a.jpg",Store.YES));
//        //找一个目录，将创建的索引文件放入这个目录下
//        Directory dir=null;
////        dir = new FSDirectory.open(new File("./index"));
//        //基于标准分词
//        Analyzer analyzer=new StandardAnalyzer();
//        //版本必须对应jar版本
//        IndexWriterConfig config=new IndexWriterConfig(analyzer);
//        //定义索引写入对象
//        IndexWriter writer=new IndexWriter(dir,config);
//        //写入数据
//        writer.addDocument(document);
//        //关闭资源
//        writer.close();
//        dir.close();
//
//    }
//}
