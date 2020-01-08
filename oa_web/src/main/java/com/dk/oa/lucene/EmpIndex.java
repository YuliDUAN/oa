package com.dk.oa.lucene;

import com.dk.oa.entity.Employee;
import com.dk.oa.global.StringUtil;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.cn.smart.SmartChineseAnalyzer;
import org.apache.lucene.document.*;
import org.apache.lucene.index.*;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.*;
import org.apache.lucene.search.highlight.*;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.wltea.analyzer.lucene.IKAnalyzer;

import java.io.StringReader;
import java.nio.file.Paths;
import java.util.LinkedList;
import java.util.List;

/*
* 利用lucene实现全局搜索
* */
public class EmpIndex {
    private Directory dir = null;
    private String lucenePath = "D://lucene";

    /*
    获取对lucene的写入方法
    * */
    private IndexWriter getWrite() throws Exception{

        dir = FSDirectory.open(Paths.get(lucenePath,new String[0]));
        Analyzer analyzer = new IKAnalyzer();
        IndexWriterConfig iwc = new IndexWriterConfig(analyzer);
        IndexWriter writer = new IndexWriter(dir,iwc);
        return writer;
    }
    /*
    * 添加索引
    * */
    public void addIndex(Employee employee)throws Exception{
        IndexWriter writer = getWrite();
        Document doc = new Document();
        //工号sn
        doc.add(new StringField("sn",employee.getSn(), Field.Store.YES));
        //姓名name
        doc.add(new StringField("name",employee.getName(),Field.Store.YES));
        //部门departmentSn
        doc.add(new StringField("departmentSn", employee.getDepartmentSn(),Field.Store.YES));
        //职位post
        doc.add(new StringField("post",employee.getPost(),Field.Store.YES));
        writer.addDocument(doc);
        writer.close();
    }
    /*
    * 更新索引
    * */
    public void updateIndex(Employee employee)throws Exception{
        IndexWriter writer = getWrite();
        Document doc = new Document();
        //工号sn
        doc.add(new StringField("sn",employee.getSn(), Field.Store.YES));
        //姓名name
        doc.add(new StringField("name",employee.getName(),Field.Store.YES));
        //部门departmentSn
        doc.add(new StringField("departmentSn", employee.getDepartmentSn(),Field.Store.YES));
        //职位post
        doc.add(new StringField("post",employee.getPost(),Field.Store.YES));
        writer.updateDocument(new Term("sn",employee.getSn()),doc);
        writer.close();
    }
    /*
    * 删除索引
    * */
    public void deleteIndex(String sn) throws Exception{
        IndexWriter writer = getWrite();
        writer.deleteDocuments(new Term[]{new Term("sn",sn)});
        writer.forceMergeDeletes();
        writer.commit();
        writer.close();
    }
    /*
    * 搜索索引
    * */
    public List<Employee> searchEmp(String q)throws Exception{

        List<Employee> empList = new LinkedList<>();
        dir = FSDirectory.open(Paths.get(lucenePath,new String[0]));
        //获取reader
        IndexReader reader = DirectoryReader.open(dir);
        //获取流
        IndexSearcher is = new IndexSearcher(reader);
        //放入查询条件
        BooleanQuery.Builder booleanQuery = new BooleanQuery.Builder();
        SmartChineseAnalyzer analyzer = new SmartChineseAnalyzer();
        //根据原因查询
        QueryParser parser = new QueryParser("sn",analyzer);
        Query query = parser.parse(q);
        //根据姓名查询
        QueryParser parser2 = new QueryParser("name",analyzer);
        Query query2 = parser2.parse(q);
        //根据部门编号查询
        QueryParser parser3 = new QueryParser("department",analyzer);
        Query query3 = parser3.parse(q);
        //根据职位查询
        QueryParser parser4 = new QueryParser("post",analyzer);
        Query query4 = parser4.parse(q);

        //并列条件，或者关系
        booleanQuery.add(query, BooleanClause.Occur.SHOULD);
        booleanQuery.add(query2, BooleanClause.Occur.SHOULD);
        booleanQuery.add(query3, BooleanClause.Occur.SHOULD);
        booleanQuery.add(query4, BooleanClause.Occur.SHOULD);

        //最多显示100条数据
        TopDocs hits = is.search(booleanQuery.build(),100);

        //高亮显示搜索字
        QueryScorer scorer = new QueryScorer(query);
        Fragmenter fragmenter = new SimpleSpanFragmenter(scorer);
        SimpleHTMLFormatter simpleHTMLFormatter = new SimpleHTMLFormatter("<b><font color='red'>","</font></b>");
        Highlighter highlighter = new Highlighter(simpleHTMLFormatter,scorer);
        highlighter.setTextFragmenter(fragmenter);

        //遍历查询结果，放入claimList
        for (ScoreDoc scoreDoc:hits.scoreDocs) {
            Document doc = is.doc(scoreDoc.doc);
            Employee employee = new Employee();

            employee.setSn(doc.get("sn"));
            String sn = doc.get("sn");
            String name = doc.get("name");
            String departmentSn = doc.get("departmentSn");
            String post = doc.get("post");
            //判断是否高亮显示
            if (sn!=null){
                TokenStream tokenStream = analyzer.tokenStream("sn",new StringReader(sn));
                String hSn = highlighter.getBestFragment(tokenStream,sn);
                if (StringUtil.isEmpty(hSn)){
                    //只高亮显示前十个字
                    if (sn.length()<10){
                        employee.setSn(sn);
                    }else {
                        employee.setSn(sn.substring(0,10));
                    }
                }else {
                    employee.setSn(hSn);
                }
            }
            if (name!=null){
                TokenStream tokenStream = analyzer.tokenStream("name",new StringReader(name));
                String hName = highlighter.getBestFragment(tokenStream,name);
                if (StringUtil.isEmpty(hName)){
                    employee.setName(name);
                }else {
                    employee.setName(hName);
                }
            }
            if (departmentSn!=null){
                TokenStream tokenStream = analyzer.tokenStream("departmentSn",new StringReader(departmentSn));
                String hDepartmentSn = highlighter.getBestFragment(tokenStream,departmentSn);
                if (StringUtil.isEmpty(hDepartmentSn)){
                    employee.setDepartmentSn(departmentSn);
                }else {
                    employee.setDepartmentSn(hDepartmentSn);
                }
            }
            if (post!=null){
                TokenStream tokenStream = analyzer.tokenStream("post",new StringReader(post));
                String hPost = highlighter.getBestFragment(tokenStream,post);
                if (StringUtil.isEmpty(hPost)){
                    employee.setPost(post);
                }else {
                    employee.setPost(hPost);
                }
            }
            empList.add(employee);
        }

        return empList;
    }
}
