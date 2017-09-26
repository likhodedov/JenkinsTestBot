import com.offbytwo.jenkins.JenkinsServer;
import com.offbytwo.jenkins.model.Build;
import com.offbytwo.jenkins.model.BuildWithDetails;
import com.offbytwo.jenkins.model.Job;
import com.offbytwo.jenkins.model.JobWithDetails;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Map;

public class Jenkins {




    public static void main(String args[]) throws IOException, URISyntaxException {

        JenkinsServer jenkins = new JenkinsServer(new URI("http://jenkins.movavi.srv/"), "testrail", "006436088f74df66c6dc51841dda999d");
//        Map<String, Job> jobs = jenkins.getJobs();
        Map<String, Job> jobs=jenkins.getJobs("Mobile");

        JobWithDetails job=jobs.get("Movavi_VideoEditor_Android").details();
        List<Build> buildList=job.getBuilds();
        Build build=buildList.get(2);
        BuildWithDetails buildWithDetails=build.details();


        buildWithDetails.getParameters();
        Map<String,String> ss=buildWithDetails.getParameters();
        buildWithDetails.getResult();
               String brunch=ss.get("branch");

        job.build();


        buildWithDetails.getChangeSet();
    }

}
