package devices;

/*    */ 
/*    */ 
/*    */ import java.util.Date;
/*    */ import java.util.Random;
/*    */ import org.eclipse.leshan.client.resource.BaseInstanceEnabler;
import org.eclipse.leshan.client.servers.ServerIdentity;
/*    */ import org.eclipse.leshan.core.response.ReadResponse;
/*    */ import org.slf4j.Logger;
/*    */ import org.slf4j.LoggerFactory;
/*    */ 
/*    */ public class MyLocation
/*    */   extends BaseInstanceEnabler
/*    */ {
/* 13 */   private static final Logger LOG = LoggerFactory.getLogger(MyLocation.class);
/*    */   
/* 15 */   private static final Random RANDOM = new Random();
/*    */   
/*    */   private float latitude;
/*    */   private float longitude;
/*    */   private float scaleFactor;
/*    */   private Date timestamp;
			ServerIdentity identity;
/*    */   
/*    */   public MyLocation() {
/* 23 */     this(null, null, 1.0F);
/*    */   }
/*    */   
/*    */   public MyLocation(Float latitude, Float longitude, float scaleFactor) {
/* 27 */     if (latitude != null) {
/* 28 */       this.latitude = latitude.floatValue() + 90.0F;
/*    */     } else {
/* 30 */       this.latitude = RANDOM.nextInt(180);
/*    */     } 
/* 32 */     if (longitude != null) {
/* 33 */       this.longitude = longitude.floatValue() + 180.0F;
/*    */     } else {
/* 35 */       this.longitude = RANDOM.nextInt(360);
/*    */     } 
/* 37 */     this.scaleFactor = scaleFactor;
/* 38 */     this.timestamp = new Date();
/*    */   }
/*    */ 
/*    */   
/*    */   public ReadResponse read(ServerIdentity identity,int resourceid) {
/* 43 */     LOG.info("Read on Location Resource " + resourceid);
/* 44 */     switch (resourceid) {
/*    */       case 0:
/* 46 */         return ReadResponse.success(resourceid, getLatitude());
/*    */       case 1:
/* 48 */         return ReadResponse.success(resourceid, getLongitude());
/*    */       case 5:
/* 50 */         return ReadResponse.success(resourceid, getTimestamp());
/*    */     } 
/* 52 */     return super.read(identity, resourceid);
/*    */   }
/*    */ 
/*    */   
/*    */   public void moveLocation(String nextMove) {
/* 57 */     switch (nextMove.charAt(0)) {
/*    */       case 'w':
/* 59 */         moveLatitude(1.0F);
/*    */         break;
/*    */       case 'a':
/* 62 */         moveLongitude(-1.0F);
/*    */         break;
/*    */       case 's':
/* 65 */         moveLatitude(-1.0F);
/*    */         break;
/*    */       case 'd':
/* 68 */         moveLongitude(1.0F);
/*    */         break;
/*    */     } 
/*    */   }
/*    */   
/*    */   private void moveLatitude(float delta) {
/* 74 */     this.latitude += delta * this.scaleFactor;
/* 75 */     this.timestamp = new Date();
/* 76 */     //fireResourcesChange(new int[] { 0, 5 });
/*    */   }
/*    */   
/*    */   private void moveLongitude(float delta) {
/* 80 */     this.longitude += delta * this.scaleFactor;
/* 81 */     this.timestamp = new Date();
/* 82 */     //fireResourcesChange(new int[] { 1, 5 });
/*    */   }
/*    */   
/*    */   public float getLatitude() {
/* 86 */     return this.latitude - 90.0F;
/*    */   }
/*    */   
/*    */   public float getLongitude() {
/* 90 */     return this.longitude - 180.0F;
/*    */   }
/*    */   
/*    */   public Date getTimestamp() {
/* 94 */     return this.timestamp;
/*    */   }
/*    */ }


/* Location:              C:\DETASAD\nokia leshan simulator\sumulators\leshan-client-demo-1.0.0-SNAPSHOT-jar-with-dependencies.jar!\org\eclipse\leshan\client\demo\MyLocation.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */