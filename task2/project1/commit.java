package project1;

import java.security.NoSuchAlgorithmException;

import static project1.hash.sha1;

public class commit extends keyvalueobject {
    public commit(String newTreeKey, String lastCommit) throws NoSuchAlgorithmException {
        type = "commit";
        key = sha1(newTreeKey + lastCommit);
        content = "new commit key:" + newTreeKey + " || old commit key:"+lastCommit;
    }

    public String getkey() {
        return key;
    }

    public String getValue() {
        return content;
    }


}
