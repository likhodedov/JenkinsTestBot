import com.offbytwo.jenkins.JenkinsServer;
import com.offbytwo.jenkins.model.Build;
import com.offbytwo.jenkins.model.Job;
import com.offbytwo.jenkins.model.JobWithDetails;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Map;

public class JenkinsDataManager {

    private Map<String,Job> jobs;
    private JobWithDetails jobAndroid;
    private JobWithDetails jobiOS;
    private List<Build> buildsAndroid;
    private List<Build> buildsiOS;


    public JenkinsDataManager() throws URISyntaxException, IOException {
        JenkinsServer jenkins = new JenkinsServer(new URI("htp://jenkins.movavi.srv/"), "testrail", "006436088f74df66c6dc51841dda999d");
        jobs=jenkins.getJobs();
        jobAndroid=jobs.get("Movavi_VideoEditor_Android").details();
        jobiOS=jobs.get("Movavi_VideoEditor_iOS").details();
        buildsAndroid=jobAndroid.getBuilds();
        buildsiOS=jobAndroid.getBuilds();

    }


    public void UpdateJenkinsData() throws IOException, URISyntaxException {
        new JenkinsDataManager();
    }

    public List<String> getJobNames(JobWithDetails job){


        return  null;
    }






}
