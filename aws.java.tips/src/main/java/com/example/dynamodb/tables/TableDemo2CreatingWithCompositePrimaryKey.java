/**
 * 
 */
package com.example.dynamodb.tables;

import java.util.Random;

import software.amazon.awssdk.services.dynamodb.DynamoDbClient;
import software.amazon.awssdk.services.dynamodb.model.AttributeDefinition;
import software.amazon.awssdk.services.dynamodb.model.CreateTableRequest;
import software.amazon.awssdk.services.dynamodb.model.CreateTableResponse;
import software.amazon.awssdk.services.dynamodb.model.DynamoDbException;
import software.amazon.awssdk.services.dynamodb.model.KeySchemaElement;
import software.amazon.awssdk.services.dynamodb.model.KeyType;
import software.amazon.awssdk.services.dynamodb.model.ProvisionedThroughput;
import software.amazon.awssdk.services.dynamodb.model.ScalarAttributeType;

/**
 * @author maurizio
 * Create a Table with a Composite Primary Key
 *
 */
public class TableDemo2CreatingWithCompositePrimaryKey {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Random xxx = new Random();
		String table_name = "TestJava"+xxx.nextInt();
		CreateTableRequest request = CreateTableRequest.builder()
			    .attributeDefinitions(
			          AttributeDefinition.builder()
			          .attributeName("Language")
			          .attributeType(ScalarAttributeType.S)
			          .build(),
			          AttributeDefinition.builder()
			          .attributeName("Greeting")
			          .attributeType(ScalarAttributeType.S)
			          .build())
			    .keySchema(
			    		KeySchemaElement.builder()
			    		.attributeName("Language")
			    		.keyType(KeyType.HASH)
			    		.build(),
			    		KeySchemaElement.builder()
			    		.attributeName("Greeting")
			    		.keyType(KeyType.RANGE)
			    		.build())
			    .provisionedThroughput(
			    		ProvisionedThroughput.builder()
			    		.readCapacityUnits(new Long(10))
			    		.writeCapacityUnits(new Long(10)).build())
			    .tableName(table_name)
			    .build();

			DynamoDbClient ddb = DynamoDbClient.create();

			try {
			    CreateTableResponse result = ddb.createTable(request);
			    System.out.format("Created table \"%s\".\n", table_name);
			} catch (DynamoDbException e) {
			    System.err.println(e.getMessage());
			    System.exit(1);
			}
	}

}
