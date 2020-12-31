package project1;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;

import static project1.hash.sha1;

public class commit extends keyvalueobject {
    public commit(String newTreeKey, String lastCommit) throws NoSuchAlgorithmException, IOException {
        type = "commit";
        key = sha1(newTreeKey + lastCommit);

        content = "更新后的文件夹key:" + newTreeKey + " || old commit key:"+lastCommit;
        copy.copyWhole(key);
    }

    public String getkey() {
        return key;
    }

    public String getValue() {
        return content;
    }


}
