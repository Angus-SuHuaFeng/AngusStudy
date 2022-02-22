package HDFS;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.*;

import java.io.IOException;

/**
 * @author Angus
 */
public class HdfsApi {
    static FileSystem fileSystem = null;
    static {
        Configuration conf = new Configuration();
        conf.set("fs.defaultFS", "hdfs://BigData1:9000");
        try {
            fileSystem = FileSystem.get(conf);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws IOException {
//        //创建文件夹
//        mkdirHdfs(new Path("/Angus"));
//        //下载文件到本地
//        getFile(new Path("/data1/text.txt"), new Path("E:\\HadoopData\\t.txt"));
//        //上传文件到hdfs
//        upload(new Path("E:\\VC_RED.cab"), new Path("/data1/VC_RED.cab"));
//        //修改文件夹名称
//        renameFile(new Path("/data/out/part-r-00000"),new Path("/data/out/part-r-00001"));
//        //输出文件信息
//        listFiles(new Path("/data1/mysql-installer-community-8.0.18.0.msi"),false);
//        //1.在HDFS的根目录下，创建名为hdfstest的目录。
//        mkdirHdfs(new Path("/hdfstest"));
//        //2.在HDFS的目录/hdfstest下，创建名为touchfile的文件
//        touchFile(new Path("/hdfstest/touchfile"));
//        //3.将本地文件E:\\VC_RED.cab，上传到HDFS文件系统的/hdfstest目录下(VC_RED.cab文件请自行创建)。
//        fileSystem.copyFromLocalFile(new Path("E:\\VC_RED.cab"),new Path("/hdfstest/VC_RED"));
//
    }

    /**
     * 文件下载
     * @param path1     hdfs的地址
     * @param path2     本地地址
     * @throws IOException  IO
     */
    private static void getFile(Path path1, Path path2) throws IOException {
        //delSrc:指的是是否删除源文件,false下载后保留hdfs中的文件,第二个是hdfs的地址,第三个是本地地址
        fileSystem.copyToLocalFile(false, path1, path2);
        fileSystem.close();
        System.out.println("下载成功");
    }

    /**
     * 创建文件夹
     */
    private static void mkdirHdfs(Path path) throws IOException {
        boolean mkdirs = fileSystem.mkdirs(path);
        fileSystem.close();
        System.out.println("文件夹创建结果:"+mkdirs);
    }

    private static void touchFile(Path path) throws IOException {
        boolean newFile = fileSystem.createNewFile(path);
        fileSystem.close();
        System.out.println("创建文件结果"+ newFile);
    }
    /**
     * 更改文件(夹)名称
     * @param path1 要修改的文件或目录
     * @param path2 修改后的名称
     * @throws IOException IO
     */
    private static void renameFile(Path path1, Path path2) throws IOException {
        boolean rename = fileSystem.rename(path1, path2);
        fileSystem.close();
        System.out.println("修改文件名称:"+ rename);
    }

    /**
     * 文件上传
     * @param path1 要上传的本地文件路径
     * @param path2 要传至hdfs的路径
     * @throws IOException IO
     */
    private static void upload(Path path1, Path path2) throws IOException {
        fileSystem.copyFromLocalFile(path1,path2);
        fileSystem.close();
        System.out.println("上传成功");
    }

    /**
     *
     * @param path  要查看的目录或文件的目录
     * @param recursive 递归
     * @throws IOException  IO
     */
    private static void listFiles(Path path, boolean recursive) throws IOException {
        RemoteIterator<LocatedFileStatus> listFiles = fileSystem.listFiles(path, recursive);
        while (listFiles.hasNext()) {
            //获取下一个值
            LocatedFileStatus fileStatus = listFiles.next();
            //获取文件名称
            System.out.println("文件名称" + fileStatus.getPath().getName());
            //获取Block块大小
            System.out.println("Block大小: " + fileStatus.getBlockSize());
            //获取权限
            System.out.println("权限" + fileStatus.getPermission());
            //块信息
            BlockLocation[] blockLocations = fileStatus.getBlockLocations();
            for (BlockLocation location:blockLocations) {
                //偏移量
                System.out.println("偏移量: "+ location.getOffset());
                //获取hosts
                String[] hosts = location.getHosts();
                for (String host : hosts) {
                    System.out.println("主机:" + host);
                }
            }

        }
        fileSystem.close();
    }
}
