import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Job {
    private String job_title;
    private Integer min_salary;
    private Integer max_salary;
    private String job_language;
    private Integer work_exp;

    public Job() {
    }

    public Job(String job_title, Integer min_salary, Integer max_salary, String job_language, Integer work_exp) {
        this.job_title = job_title;
        this.min_salary = min_salary;
        this.max_salary = max_salary;
        this.job_language = job_language;
        this.work_exp = work_exp;
    }


    public String getJob_title() {
        return job_title;
    }

    public void setJob_title(String job_title) {
        this.job_title = job_title;
    }

    public Integer getMin_salary() {
        return min_salary;
    }

    public void setMin_salary(Integer min_salary) {
        this.min_salary = min_salary;
    }

    public Integer getMax_salary() {
        return max_salary;
    }

    public void setMax_salary(Integer max_salary) {
        this.max_salary = max_salary;
    }

    public String getJob_language() {
        return job_language;
    }

    public void setJob_language(String job_language) {
        this.job_language = job_language;
    }

    public Integer getWork_exp() {
        return work_exp;
    }

    public void setWork_exp(Integer work_exp) {
        this.work_exp = work_exp;
    }


    @Override
    public String toString() {
        return "Job{" + "title=" + job_title + ", min salary=" + min_salary + "," +
                " max salary=" + max_salary + ", job language=" + job_language + ", " +
                "work exp=" + work_exp + '}';
    }
}