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
 * Create a Table with a Simple Primary Key
 *
 */
public class TableDemo1CreatingWithSimplePrimaryKey {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		final String USAGE = "\n" + "Usage:\n" + "    CreateTable <table>\n\n" + "Where:\n"
				+ "    table - the table to create.\n\n" + "Example:\n" + "    CreateTable HelloTable\n";

		if (args.length < 1) {
			System.out.println(USAGE);
//			System.exit(1);
		}

		/* Read the name from command args */
		//String table_name = args[0];
		Random xxx = new Random();
		
		String table_name = "TestJava"+xxx.nextInt();
		
		//a Simple Primary Key
		String columnName = "column_name_demo" ;
		
		System.out.format("Creating table \"%s\" with a simple primary key: \"%s\".\n", table_name, columnName);

		
		// snippet-start:[dynamodb.java2.create_table.main]
		CreateTableRequest request = CreateTableRequest.builder()
				.attributeDefinitions(AttributeDefinition.builder().attributeName(columnName)
						.attributeType(ScalarAttributeType.S).build())
				.keySchema(KeySchemaElement.builder().attributeName(columnName).keyType(KeyType.HASH).build())
				.provisionedThroughput(ProvisionedThroughput.builder().readCapacityUnits(new Long(10))
						.writeCapacityUnits(new Long(10)).build())
				.tableName(table_name).build();

		DynamoDbClient ddb = DynamoDbClient.create();

		try {
			CreateTableResponse response = ddb.createTable(request);
			System.out.println("xxx: " + response.tableDescription().tableName());
		} catch (DynamoDbException e) {
			System.err.println(e.getMessage());
			System.exit(1);
		}
		// snippet-end:[dynamodb.java2.create_table.main]
		System.out.println("Done!");
	}

}
