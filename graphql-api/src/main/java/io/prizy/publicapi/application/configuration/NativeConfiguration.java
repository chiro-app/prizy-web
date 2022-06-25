package io.prizy.publicapi.application.configuration;

import graphql.GraphQL;
import graphql.analysis.QueryVisitorFieldArgumentEnvironment;
import graphql.analysis.QueryVisitorFieldArgumentInputValue;
import graphql.execution.Execution;
import graphql.execution.nextgen.result.RootExecutionResultNode;
import graphql.language.Argument;
import graphql.language.ArrayValue;
import graphql.language.BooleanValue;
import graphql.language.Directive;
import graphql.language.DirectiveDefinition;
import graphql.language.DirectiveLocation;
import graphql.language.Document;
import graphql.language.EnumTypeDefinition;
import graphql.language.EnumTypeExtensionDefinition;
import graphql.language.EnumValue;
import graphql.language.EnumValueDefinition;
import graphql.language.Field;
import graphql.language.FieldDefinition;
import graphql.language.FloatValue;
import graphql.language.FragmentDefinition;
import graphql.language.FragmentSpread;
import graphql.language.ImplementingTypeDefinition;
import graphql.language.InlineFragment;
import graphql.language.InputObjectTypeDefinition;
import graphql.language.InputObjectTypeExtensionDefinition;
import graphql.language.InputValueDefinition;
import graphql.language.IntValue;
import graphql.language.InterfaceTypeDefinition;
import graphql.language.InterfaceTypeExtensionDefinition;
import graphql.language.ListType;
import graphql.language.NonNullType;
import graphql.language.NullValue;
import graphql.language.ObjectField;
import graphql.language.ObjectTypeDefinition;
import graphql.language.ObjectTypeExtensionDefinition;
import graphql.language.ObjectValue;
import graphql.language.OperationDefinition;
import graphql.language.OperationTypeDefinition;
import graphql.language.ScalarTypeDefinition;
import graphql.language.ScalarTypeExtensionDefinition;
import graphql.language.SchemaDefinition;
import graphql.language.SchemaExtensionDefinition;
import graphql.language.SelectionSet;
import graphql.language.StringValue;
import graphql.language.TypeDefinition;
import graphql.language.TypeName;
import graphql.language.UnionTypeDefinition;
import graphql.language.UnionTypeExtensionDefinition;
import graphql.language.VariableDefinition;
import graphql.language.VariableReference;
import graphql.parser.ParserOptions;
import graphql.schema.DataFetchingEnvironment;
import graphql.schema.GraphQLArgument;
import graphql.schema.GraphQLCodeRegistry;
import graphql.schema.GraphQLDirective;
import graphql.schema.GraphQLEnumType;
import graphql.schema.GraphQLEnumValueDefinition;
import graphql.schema.GraphQLFieldDefinition;
import graphql.schema.GraphQLInputObjectField;
import graphql.schema.GraphQLInputObjectType;
import graphql.schema.GraphQLInterfaceType;
import graphql.schema.GraphQLList;
import graphql.schema.GraphQLNamedType;
import graphql.schema.GraphQLNonNull;
import graphql.schema.GraphQLObjectType;
import graphql.schema.GraphQLOutputType;
import graphql.schema.GraphQLScalarType;
import graphql.schema.GraphQLSchema;
import graphql.schema.GraphQLSchemaElement;
import graphql.schema.GraphQLUnionType;
import graphql.schema.validation.SchemaValidationErrorCollector;
import graphql.util.NodeAdapter;
import graphql.util.NodeZipper;
import org.springframework.context.annotation.Configuration;
import org.springframework.nativex.hint.NativeHint;
import org.springframework.nativex.hint.ResourceHint;
import org.springframework.nativex.hint.TypeHint;

import java.util.List;

import static org.springframework.nativex.hint.TypeAccess.DECLARED_CLASSES;
import static org.springframework.nativex.hint.TypeAccess.DECLARED_CONSTRUCTORS;
import static org.springframework.nativex.hint.TypeAccess.DECLARED_FIELDS;
import static org.springframework.nativex.hint.TypeAccess.DECLARED_METHODS;
import static org.springframework.nativex.hint.TypeAccess.PUBLIC_CLASSES;
import static org.springframework.nativex.hint.TypeAccess.PUBLIC_CONSTRUCTORS;
import static org.springframework.nativex.hint.TypeAccess.PUBLIC_FIELDS;
import static org.springframework.nativex.hint.TypeAccess.PUBLIC_METHODS;

/**
 * @author Nidhal Dogga
 * @created 6/24/2022 8:38 PM
 */


@Configuration
@TypeHint(
  typeNames = {
    "graphql.analysis.QueryTraversalContext",
    "graphql.schema.idl.SchemaParseOrder",
  },
  types = {
    Argument.class,
    ArrayValue.class,
    Boolean.class,
    BooleanValue.class,
    DataFetchingEnvironment.class,
    Directive.class,
    DirectiveDefinition.class,
    DirectiveLocation.class,
    Document.class,
    EnumTypeDefinition.class,
    EnumTypeExtensionDefinition.class,
    EnumValue.class,
    EnumValueDefinition.class,
    Execution.class,
    Field.class,
    FieldDefinition.class,
    FloatValue.class,
    FragmentDefinition.class,
    FragmentSpread.class,
    GraphQL.class,
    GraphQLArgument.class,
    GraphQLCodeRegistry.Builder.class,
    GraphQLDirective.class,
    GraphQLEnumType.class,
    GraphQLEnumValueDefinition.class,
    GraphQLFieldDefinition.class,
    GraphQLInputObjectField.class,
    GraphQLInputObjectType.class,
    GraphQLInterfaceType.class,
    GraphQLList.class,
    GraphQLNamedType.class,
    GraphQLNonNull.class,
    GraphQLObjectType.class,
    GraphQLOutputType.class,
    GraphQLScalarType.class,
    GraphQLSchema.class,
    GraphQLSchemaElement.class,
    GraphQLUnionType.class,
    ImplementingTypeDefinition.class,
    InlineFragment.class,
    InputObjectTypeDefinition.class,
    InputObjectTypeExtensionDefinition.class,
    InputValueDefinition.class,
    IntValue.class,
    InterfaceTypeDefinition.class,
    InterfaceTypeExtensionDefinition.class,
    List.class,
    ListType.class,
    NodeAdapter.class,
    NodeZipper.class,
    NonNullType.class,
    NullValue.class,
    ObjectField.class,
    ObjectTypeDefinition.class,
    ObjectTypeExtensionDefinition.class,
    ObjectValue.class,
    OperationDefinition.class,
    OperationTypeDefinition.class,
    ParserOptions.class,
    QueryVisitorFieldArgumentEnvironment.class,
    QueryVisitorFieldArgumentInputValue.class,
    RootExecutionResultNode.class,
    ScalarTypeDefinition.class,
    ScalarTypeExtensionDefinition.class,
    SchemaDefinition.class,
    SchemaExtensionDefinition.class,
    SchemaValidationErrorCollector.class,
    SelectionSet.class,
    StringValue.class,
    TypeDefinition.class,
    TypeName.class,
    UnionTypeDefinition.class,
    UnionTypeExtensionDefinition.class,
    VariableDefinition.class,
    VariableReference.class,
  },
  access = {
    PUBLIC_CLASSES, PUBLIC_CONSTRUCTORS, PUBLIC_FIELDS, PUBLIC_METHODS,
    DECLARED_CLASSES, DECLARED_CONSTRUCTORS, DECLARED_FIELDS, DECLARED_METHODS
  }
)
@ResourceHint(patterns = {
  "graphiql/index.html",
  "graphql/schema.graphqls",
  "db/migration/*",
  "default-assets/*",
  "templates/*"
})
public class NativeConfiguration {
}
