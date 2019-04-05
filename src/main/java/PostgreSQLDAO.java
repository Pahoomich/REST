import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PostgreSQLDAO {

    public List<Job> getAllJobs() {
        List<Job> jobs = new ArrayList<>();
        try (Connection connection = ConnectionUtil.getConnection()) {
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery("select * from job");
            while (rs.next()) {
                String job_title = rs.getString("job_title");
                Integer min_salary = rs.getInt("min_salary");
                Integer max_salary = rs.getInt("max_salary");
                String job_language = rs.getString("job_language");
                Integer work_exp = rs.getInt("work_exp");
                Job job = new Job(job_title, min_salary, max_salary, job_language, work_exp);
                jobs.add(job);
            }
        } catch (SQLException ex) {
            Logger.getLogger(PostgreSQLDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return jobs;
    }


    public List<Job> searchByArg(Job n) {
        List<Job> jobs = new ArrayList<>();
        String res = getSQL(n.getJob_title(), n.getMin_salary(), n.getMax_salary(), n.getJob_language(), n.getWork_exp());
        try (Connection connection = ConnectionUtil.getConnection()) {
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(res);
            while (rs.next()) {
                String job_title = rs.getString("job_title");
                Integer min_salary = rs.getInt("min_salary");
                Integer max_salary = rs.getInt("max_salary");
                String job_language = rs.getString("job_language");
                Integer work_exp = rs.getInt("work_exp");
                Job job = new Job(job_title, min_salary, max_salary, job_language, work_exp);
                jobs.add(job);
            }
        } catch (SQLException ex) {
            Logger.getLogger(PostgreSQLDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return jobs;
    }

    public boolean searchById(Integer id){
        boolean myflag = true;
        try (Connection connection = ConnectionUtil.getConnection()) {
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM job WHERE job_id = " + Integer.toString(id));
            myflag = rs.next();
        } catch (SQLException ex) {
            Logger.getLogger(PostgreSQLDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return myflag;
    }

    public int addNew(Job n) {
        int newID = 0;
        try (Connection connection = ConnectionUtil.getConnection()) {
            PreparedStatement rs = connection.prepareStatement("insert into job (job_title, min_salary, max_salary, job_language, work_exp)" +
                    " values (?,?,?,?,?)");
            rs.setString(1, n.getJob_title());
            rs.setInt(2, n.getMin_salary());
            rs.setInt(3, n.getMax_salary());
            rs.setString(4, n.getJob_language());
            rs.setInt(5, n.getWork_exp());
            rs.executeUpdate();

            //вернем последнее добавленное id
            Statement stmt = connection.createStatement();//вызываем createStatement потому-что select в prepareStatement вызывает ошибку (новые версии)
            ResultSet resultSet = stmt.executeQuery("SELECT currval('\"JOB_job_id_seq\"')");
            resultSet.next();
            newID = resultSet.getInt("currval");
        } catch (SQLException ex) {
            Logger.getLogger(PostgreSQLDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return newID;
    }

    public int updateRecord(int id, Job n) {
        int affectedrows = 0;
        try (Connection connection = ConnectionUtil.getConnection()) {
            PreparedStatement rs = connection.prepareStatement("UPDATE job "
                    + "SET job_title = ? , min_salary = ?, max_salary = ?, job_language = ?, work_exp = ?"
                    + "WHERE job_id = ?");

            rs.setString(1, n.getJob_title());
            rs.setInt(2, n.getMin_salary());
            rs.setInt(3, n.getMax_salary());
            rs.setString(4, n.getJob_language());
            rs.setInt(5, n.getWork_exp());
            rs.setInt(6, id);
            affectedrows = rs.executeUpdate();

        } catch (SQLException ex) {
            Logger.getLogger(PostgreSQLDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return affectedrows;
    }

    public int dropRecord(int id) {
        int affectedrows = 0;
        try (Connection connection = ConnectionUtil.getConnection()) {
            PreparedStatement rs = connection.prepareStatement("DELETE FROM job WHERE job_id = ?");
            rs.setInt(1, id);
            affectedrows = rs.executeUpdate();

        } catch (SQLException ex) {
            Logger.getLogger(PostgreSQLDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return affectedrows;
    }


    private String getSQL(String title, Integer minSalary, Integer maxSalary, String language, Integer workExp) {
        String sql = "select * from job";
        ArrayList<String> list = new ArrayList<>();

        if (title != null) {
            list.add("JOB_TITLE = " + "'" + title + "'");
        }
        if (minSalary != null) {
            list.add("MIN_SALARY = " + "'" + minSalary + "'");
        }
        if (maxSalary != null) {
            list.add("MAX_SALARY = " + "'" + maxSalary + "'");
        }
        if (language != null) {
            list.add("JOB_LANGUAGE = " + "'" + language + "'");
        }
        if (workExp != null) {
            list.add("WORK_EXP = " + "'" + workExp + "'");
        }
        if (list.size() != 0) {
            sql += " WHERE ";
        }
        for (int i = 0; i < list.size(); i++) {
            String item = list.get(i);
            if (i != list.size() - 1) {
                item += " AND ";
            }
            sql += item;
        }
        return sql;
    }


/*    public static void main(String[] args) {
        PostgreSQLDAO pers = new PostgreSQLDAO();
        //Job jobNew = new Job("Алеша", 1, 2, "русский", 0);
        //pers.addNew(jobNew);
            //pers.dropRecord(i);
        //pers.updateRecord(27,jobNew);
        Job jobNew = new Job();
        pers.searchById(2);
    }*/

}