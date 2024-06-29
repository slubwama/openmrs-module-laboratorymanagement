

def formatForeignKeys(tableName):
    block = """<changeSet author="dev" id="1658855877841-92">
        <preConditions onFail="MARK_RAN">
            <foreignKeyConstraintExists foreignKeyName="xxxx_changed_by_fk" />
        </preConditions>
        <addForeignKeyConstraint baseColumnNames="changed_by" baseTableName="xxxx" constraintName="xxxx_changed_by_fk" deferrable="false" initiallyDeferred="false" onDelete="NO ACTION" onUpdate="NO ACTION" referencedColumnNames="user_id" referencedTableName="users" />
    </changeSet>
    <changeSet author="dev" id="1658855877841-93">
        <preConditions onFail="MARK_RAN">
            <foreignKeyConstraintExists foreignKeyName="xxxx_creator_fk" />
        </preConditions>
        <addForeignKeyConstraint baseColumnNames="creator" baseTableName="xxxx" constraintName="xxxx_creator_fk" deferrable="false" initiallyDeferred="false" onDelete="NO ACTION" onUpdate="NO ACTION" referencedColumnNames="user_id" referencedTableName="users" />
    </changeSet>
    <changeSet author="dev" id="1658855877841-94">
        <preConditions onFail="MARK_RAN">
            <foreignKeyConstraintExists foreignKeyName="xxxx_voided_by_fk" />
        </preConditions>
        <addForeignKeyConstraint baseColumnNames="voided_by" baseTableName="xxxx" constraintName="xxxx_voided_by_fk" deferrable="false" initiallyDeferred="false" onDelete="NO ACTION" onUpdate="NO ACTION" referencedColumnNames="user_id" referencedTableName="users" />
    </changeSet>"""
    return block.replace("xxxx", tableName);




table_names = ['stockmgmt_activity_process','stockmgmt_activity_process_job','stockmgmt_current_activity_process_job','stockmgmt_stock_rule','stockmgmt_user_role_scope','stockmgmt_user_role_scope_location','stockmgmt_user_role_scope_operation_type','stockmgmt_workflow','stockmgmt_workflow_location','stockmgmt_workflow_process','stockmgmt_workflow_process_job','stockmgmt_workflow_stock_operation_type']
for table in table_names:
    print(formatForeignKeys(table))
    print("\r\n")
