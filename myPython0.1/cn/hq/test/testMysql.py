import MySQLdb
import time

conn = MySQLdb.connect(host='113.209.100.180' ,port=3316 ,user='root' ,passwd='568root' ,db='568db')

while True :
    try:
        print("start SELECT")
        cursor = conn.cursor()
        cursor.execute("SELECT id , t.message_content FROM wx_message t WHERE t.issend = 'N'")
        data = cursor.fetchall()
        print("select count:%d" % len(data))
        lst = []
        for row in data:
            message_id="%d" % row[0]
            lst.append(message_id)
            message_content=row[1]
            #print ('message_id=%s,message_content=%s' % (message_id,message_content))
        #print(','.join(lst))
        print("start update")
        if len(data) > 0 :
            updateSql = "update wx_message set issend='Y' where id in (" + ','.join(lst) + ")"
            print(updateSql)
            try:
                cursor.execute(updateSql)
                print(cursor.rowcount)
                conn.commit()       
            except:
                conn.rollback()         
        else : 
            print("no record")
        
        time.sleep(5000)
    except:
            print("db excute error")
    
conn.close()