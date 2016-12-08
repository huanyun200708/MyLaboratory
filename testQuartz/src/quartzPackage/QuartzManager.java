//�򵥵����������
//QuartzManager.java

package quartzPackage;

import java.text.ParseException;

import org.quartz.CronExpression;
import org.quartz.CronScheduleBuilder;
import org.quartz.CronTrigger;
import org.quartz.Job;
import org.quartz.JobBuilder;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SchedulerFactory;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.TriggerKey;
import org.quartz.impl.StdSchedulerFactory;
import org.quartz.JobDetail;

/** *//**
 * @Title:Quartz������
 * 
 * @Description:
 * 
 * @Copyright: 
 * @author zz  2008-10-8 14:19:01
 * @version 1.00.000
 *
 */
public class QuartzManager {
   private static SchedulerFactory sf = new StdSchedulerFactory();
   private static String JOB_GROUP_NAME = "group1";
   private static String TRIGGER_GROUP_NAME = "trigger1";
  
   
   /** *//**
    *  ���һ����ʱ����ʹ��Ĭ�ϵ�������������������������������
    * @param jobName ������
    * @param job     ����
    * @param time    ʱ�����ã��ο�quartz˵���ĵ�
    * @throws SchedulerException
    * @throws ParseException
    */
   public static void addJob(String jobName,Job job,String time) 
                               throws SchedulerException, ParseException{
       Scheduler sched = sf.getScheduler();
       
       //�������������飬����ִ����
       JobDetail jobDetail = JobBuilder.newJob(job.getClass())
    	       .withIdentity(new JobKey(jobName, JOB_GROUP_NAME)).build();
       //������ ��������,��������
       CronTrigger trigger = TriggerBuilder.newTrigger()
           .withIdentity(new TriggerKey(jobName, TRIGGER_GROUP_NAME))
           .withSchedule(CronScheduleBuilder.cronSchedule(new CronExpression(time))).build();
       
       sched.scheduleJob(jobDetail,trigger);
       //����
       if(!sched.isShutdown())
          sched.start();
   }
   
   /** *//**
    * ���һ����ʱ����
    * @param jobName ������
    * @param jobGroupName ��������
    * @param triggerName  ��������
    * @param triggerGroupName ����������
    * @param job     ����
    * @param time    ʱ�����ã��ο�quartz˵���ĵ�
    * @throws SchedulerException
    * @throws ParseException
    */
   public static void addJob(String jobName,String jobGroupName,
                             String triggerName,String triggerGroupName,
                             Job job,String time) 
                               throws SchedulerException, ParseException{
       Scheduler sched = sf.getScheduler();
     //�������������飬����ִ����
       JobDetail jobDetail = JobBuilder.newJob(job.getClass())
    	       .withIdentity(new JobKey(jobName, JOB_GROUP_NAME)).build();
       //������ ��������,��������
       CronTrigger trigger = TriggerBuilder.newTrigger()
           .withIdentity(new TriggerKey(triggerName, TRIGGER_GROUP_NAME))
           .withSchedule(CronScheduleBuilder.cronSchedule(new CronExpression(time))).build();
       
       sched.scheduleJob(jobDetail,trigger);
       if(!sched.isShutdown())
          sched.start();
   }
   
   /** *//**
    * �޸�һ������Ĵ���ʱ��(ʹ��Ĭ�ϵ�������������������������������)
    * @param jobName
    * @param time
    * @throws SchedulerException
    * @throws ParseException
    */
   public static void modifyJobTime(String jobName,String time) 
                                  throws SchedulerException, ParseException{
       Scheduler sched = sf.getScheduler();
       Trigger trigger =  sched.getTrigger(new TriggerKey(jobName, TRIGGER_GROUP_NAME));
       if(trigger != null){
           CronTrigger  ct = (CronTrigger)trigger;
           ct.setCronExpression(time);
           sched.resumeTrigger(new TriggerKey(jobName, TRIGGER_GROUP_NAME));
       }
   }
   
   /** *//**
    * �޸�һ������Ĵ���ʱ��
    * @param triggerName
    * @param triggerGroupName
    * @param time
    * @throws SchedulerException
    * @throws ParseException
    */
   public static void modifyJobTime(String triggerName,String triggerGroupName,
                                    String time) 
                                  throws SchedulerException, ParseException{
       Scheduler sched = sf.getScheduler();
       Trigger trigger =  sched.getTrigger(new TriggerKey(triggerName,triggerGroupName));
       if(trigger != null){
           CronTrigger  ct = (CronTrigger)trigger;
           //�޸�ʱ��
           ct.setCronExpression(time);
           //����������
           sched.resumeTrigger(new TriggerKey(triggerName,triggerGroupName));
       }
   }
   
   /** *//**
    * �Ƴ�һ������(ʹ��Ĭ�ϵ�������������������������������)
    * @param jobName
    * @throws SchedulerException
    */
   public static void removeJob(String jobName) 
                               throws SchedulerException{
       Scheduler sched = sf.getScheduler();
       sched.pauseTrigger(new TriggerKey(jobName,TRIGGER_GROUP_NAME));//ֹͣ������
       sched.unscheduleJob(new TriggerKey(jobName,TRIGGER_GROUP_NAME));//�Ƴ�������
       sched.deleteJob(new JobKey(jobName,JOB_GROUP_NAME));//ɾ������
   }
   
   /** *//**
    * �Ƴ�һ������
    * @param jobName
    * @param jobGroupName
    * @param triggerName
    * @param triggerGroupName
    * @throws SchedulerException
    */
   public static void removeJob(String jobName,String jobGroupName,
                                String triggerName,String triggerGroupName) 
                               throws SchedulerException{
       Scheduler sched = sf.getScheduler();
       sched.pauseTrigger(new TriggerKey(triggerName,triggerGroupName));//ֹͣ������
       sched.unscheduleJob(new TriggerKey(triggerName,triggerGroupName));//�Ƴ�������
       sched.deleteJob(new JobKey(jobName,jobGroupName));//ɾ������
   }
}

