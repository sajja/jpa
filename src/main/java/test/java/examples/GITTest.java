package test.java.examples;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.revwalk.RevObject;
import org.eclipse.jgit.revwalk.RevWalk;
import org.eclipse.jgit.storage.file.FileRepositoryBuilder;

import java.io.File;
import java.io.IOException;

public class GITTest {

    public static void main(String[] args) throws IOException, GitAPIException {
        FileRepositoryBuilder repositoryBuilder = new FileRepositoryBuilder();
        Repository repository = repositoryBuilder.setGitDir(new File("/home/sajith/work/paysol/.git"))
                .readEnvironment() // scan environment GIT_* variables
                .findGitDir() // scan up the file system tree
                .setMustExist(true)
                .build();
        System.out.println(repository);
        Git git = new Git(repository);
        RevWalk walk = new RevWalk(repository);
        int  i = 0;
        for (RevCommit revCommit : git.log().all().call()) {
            System.out.println(revCommit);
            RevObject ro = walk.parseAny(revCommit);
//            RevTag rt = walk.parseTag(revCommit);
            System.out.println(ro.getClass().getName());
            if(i == 1) {
                break;
            }
            i++;
        }
        git.log().all().call();

        System.out.println(Git.wrap(repository).describe().setTarget(ObjectId.fromString("e321a29432d3c03a2b88387036338c2bf14a24f8")).call());

    }
}
