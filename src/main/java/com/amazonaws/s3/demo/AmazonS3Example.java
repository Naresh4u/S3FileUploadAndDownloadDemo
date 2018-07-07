package com.amazonaws.s3.demo;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.Bucket;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.GetObjectRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectInputStream;
public class AmazonS3Example {
	
	private static final String SUFFIX = "/";
	
			
	public static void main(String[] args) {
		// credentials object identifying user for authentication
		// user must have AWSConnector and AmazonS3FullAccess for 
		// this example to work
		AWSCredentials credentials = new BasicAWSCredentials("AKIAIVA3B6JDZ3TPUHQQ", "UA2NylXICqSgk5NEAE4Ys0VHvhJYJ5tkZo1DgQ/A");
			
		// create a client connection based on credentials
		AmazonS3 s3client = new AmazonS3Client(credentials);
		
		// create bucket - name must be unique for all S3 users -aws-s3-upload-bucket
		String bucketName = "aws-s3-upload-bucket";
		s3client.createBucket(bucketName);
		
		// create folder into bucket - upload
		String folderName = "upload";
		createFolder(bucketName, folderName, s3client);
				
		// upload file to folder and set it to public -- Greetings.txt
		String fileName = folderName + SUFFIX + "Greetings.txt";
		s3client.putObject(new PutObjectRequest(bucketName, fileName, 
				new File("C:\\DNaresh\\AWS-S3\\Upload\\Greetings.txt"))
				.withCannedAcl(CannedAccessControlList.PublicRead));
		System.out.println("File upload is done, file uploaded S3 path : "+bucketName+"/"+fileName);
		
		// download file from s3 bucket to local
		File localFile = new File("C:/DNaresh/AWS-S3/Download/Greetings.txt");
		System.out.println("bucketName : "+bucketName);
		System.out.println("fileName : "+fileName.substring(7, fileName.length()));
		ObjectMetadata object = s3client.getObject(new GetObjectRequest(bucketName, fileName), localFile);
		
		System.out.println("File download is done, file downloaded local path : "+localFile);
	}
	
	public static void createFolder(String bucketName, String folderName, AmazonS3 client) {
		// create meta-data for your folder and set content-length to 0
		ObjectMetadata metadata = new ObjectMetadata();
		metadata.setContentLength(0);
		// create empty content
		InputStream emptyContent = new ByteArrayInputStream(new byte[0]);
		// create a PutObjectRequest passing the folder name suffixed by /
		PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName,
				folderName + SUFFIX, emptyContent, metadata);
		// send request to S3 to create folder
		client.putObject(putObjectRequest);
	}
	
/*	public void dowloadFromS3ToLocal()
	{
		AmazonS3Client s3Client = new AmazonS3Client(credentials);

		File localFile = new File("C:\\DNaresh\\AWS-S3\\Download\\");

		ObjectMetadata object = s3Client.getObject(new GetObjectRequest("bucket", "s3FileName"), localFile);
	}
*/
}