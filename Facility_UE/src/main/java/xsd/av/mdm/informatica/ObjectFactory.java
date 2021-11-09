
package xsd.av.mdm.informatica;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.bind.annotation.adapters.HexBinaryAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import javax.xml.datatype.XMLGregorianCalendar;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the xsd.av.mdm.informatica package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _ProcessTaskRequest_QNAME = new QName("urn:informatica.mdm.av.xsd", "processTaskRequest");
    private final static QName _StartProcessRequest_QNAME = new QName("urn:informatica.mdm.av.xsd", "startProcessRequest");
    private final static QName _ProcessTaskResponse_QNAME = new QName("urn:informatica.mdm.av.xsd", "processTaskResponse");
    private final static QName _StartProcessResponse_QNAME = new QName("urn:informatica.mdm.av.xsd", "startProcessResponse");
    private final static QName _TaskDataOwnerUID_QNAME = new QName("urn:informatica.mdm.av.xsd", "ownerUID");
    private final static QName _TaskDataStatus_QNAME = new QName("urn:informatica.mdm.av.xsd", "status");
    private final static QName _TaskDataCreateDate_QNAME = new QName("urn:informatica.mdm.av.xsd", "createDate");
    private final static QName _TaskDataTaskId_QNAME = new QName("urn:informatica.mdm.av.xsd", "taskId");
    private final static QName _TaskDataSubjectAreaUID_QNAME = new QName("urn:informatica.mdm.av.xsd", "subjectAreaUID");
    private final static QName _TaskDataTaskRecords_QNAME = new QName("urn:informatica.mdm.av.xsd", "taskRecords");
    private final static QName _TaskDataPriority_QNAME = new QName("urn:informatica.mdm.av.xsd", "priority");
    private final static QName _TaskDataTitle_QNAME = new QName("urn:informatica.mdm.av.xsd", "title");
    private final static QName _TaskDataComments_QNAME = new QName("urn:informatica.mdm.av.xsd", "comments");
    private final static QName _TaskDataUpdatedBy_QNAME = new QName("urn:informatica.mdm.av.xsd", "updatedBy");
    private final static QName _TaskDataInteractionId_QNAME = new QName("urn:informatica.mdm.av.xsd", "interactionId");
    private final static QName _TaskDataCreator_QNAME = new QName("urn:informatica.mdm.av.xsd", "creator");
    private final static QName _TaskDataDueDate_QNAME = new QName("urn:informatica.mdm.av.xsd", "dueDate");
    private final static QName _TaskDataLastUpdateDate_QNAME = new QName("urn:informatica.mdm.av.xsd", "lastUpdateDate");
    private final static QName _INFATaskTaskData_QNAME = new QName("urn:informatica.mdm.av.xsd", "taskData");
    private final static QName _INFATaskWorkflowVersion_QNAME = new QName("urn:informatica.mdm.av.xsd", "workflowVersion");
    private final static QName _INFATaskOrsId_QNAME = new QName("urn:informatica.mdm.av.xsd", "orsId");
    private final static QName _INFATaskActions_QNAME = new QName("urn:informatica.mdm.av.xsd", "actions");
    private final static QName _INFATaskHubPassword_QNAME = new QName("urn:informatica.mdm.av.xsd", "hubPassword");
    private final static QName _INFATaskTaskType_QNAME = new QName("urn:informatica.mdm.av.xsd", "taskType");
    private final static QName _INFATaskHubUsername_QNAME = new QName("urn:informatica.mdm.av.xsd", "hubUsername");
    private final static QName _INFATaskSecurityPayload_QNAME = new QName("urn:informatica.mdm.av.xsd", "securityPayload");
    private final static QName _INFARecordKeyRowidXref_QNAME = new QName("urn:informatica.mdm.av.xsd", "rowidXref");
    private final static QName _INFARecordKeyPkeySrcObject_QNAME = new QName("urn:informatica.mdm.av.xsd", "pkeySrcObject");
    private final static QName _INFARecordKeyRowId_QNAME = new QName("urn:informatica.mdm.av.xsd", "rowId");
    private final static QName _INFARecordKeySystem_QNAME = new QName("urn:informatica.mdm.av.xsd", "system");
    private final static QName _INFARecordKeyTableUID_QNAME = new QName("urn:informatica.mdm.av.xsd", "tableUID");
    private final static QName _INFATaskActionDescription_QNAME = new QName("urn:informatica.mdm.av.xsd", "description");
    private final static QName _INFATaskActionName_QNAME = new QName("urn:informatica.mdm.av.xsd", "name");
    private final static QName _INFATaskActionCancelTask_QNAME = new QName("urn:informatica.mdm.av.xsd", "cancelTask");
    private final static QName _INFATaskActionCloseTaskView_QNAME = new QName("urn:informatica.mdm.av.xsd", "closeTaskView");
    private final static QName _INFATaskActionManualReassign_QNAME = new QName("urn:informatica.mdm.av.xsd", "manualReassign");
    private final static QName _INFATaskActionDisplayName_QNAME = new QName("urn:informatica.mdm.av.xsd", "displayName");
    private final static QName _TaskTypeDataUpdateType_QNAME = new QName("urn:informatica.mdm.av.xsd", "dataUpdateType");
    private final static QName _TaskTypeDisplayType_QNAME = new QName("urn:informatica.mdm.av.xsd", "displayType");
    private final static QName _TaskTypePendingBVT_QNAME = new QName("urn:informatica.mdm.av.xsd", "pendingBVT");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: xsd.av.mdm.informatica
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link INFATask }
     * 
     */
    public INFATask createINFATask() {
        return new INFATask();
    }

    /**
     * Create an instance of {@link StartProcessResponse }
     * 
     */
    public StartProcessResponse createStartProcessResponse() {
        return new StartProcessResponse();
    }

    /**
     * Create an instance of {@link ProcessTaskRequest }
     * 
     */
    public ProcessTaskRequest createProcessTaskRequest() {
        return new ProcessTaskRequest();
    }

    /**
     * Create an instance of {@link StartProcessRequest }
     * 
     */
    public StartProcessRequest createStartProcessRequest() {
        return new StartProcessRequest();
    }

    /**
     * Create an instance of {@link ProcessTaskResponse }
     * 
     */
    public ProcessTaskResponse createProcessTaskResponse() {
        return new ProcessTaskResponse();
    }

    /**
     * Create an instance of {@link TaskRecords }
     * 
     */
    public TaskRecords createTaskRecords() {
        return new TaskRecords();
    }

    /**
     * Create an instance of {@link TaskType }
     * 
     */
    public TaskType createTaskType() {
        return new TaskType();
    }

    /**
     * Create an instance of {@link INFATaskAction }
     * 
     */
    public INFATaskAction createINFATaskAction() {
        return new INFATaskAction();
    }

    /**
     * Create an instance of {@link INFARecordKey }
     * 
     */
    public INFARecordKey createINFARecordKey() {
        return new INFARecordKey();
    }

    /**
     * Create an instance of {@link TaskActions }
     * 
     */
    public TaskActions createTaskActions() {
        return new TaskActions();
    }

    /**
     * Create an instance of {@link TaskData }
     * 
     */
    public TaskData createTaskData() {
        return new TaskData();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ProcessTaskRequest }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "urn:informatica.mdm.av.xsd", name = "processTaskRequest")
    public JAXBElement<ProcessTaskRequest> createProcessTaskRequest(ProcessTaskRequest value) {
        return new JAXBElement<ProcessTaskRequest>(_ProcessTaskRequest_QNAME, ProcessTaskRequest.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link StartProcessRequest }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "urn:informatica.mdm.av.xsd", name = "startProcessRequest")
    public JAXBElement<StartProcessRequest> createStartProcessRequest(StartProcessRequest value) {
        return new JAXBElement<StartProcessRequest>(_StartProcessRequest_QNAME, StartProcessRequest.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ProcessTaskResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "urn:informatica.mdm.av.xsd", name = "processTaskResponse")
    public JAXBElement<ProcessTaskResponse> createProcessTaskResponse(ProcessTaskResponse value) {
        return new JAXBElement<ProcessTaskResponse>(_ProcessTaskResponse_QNAME, ProcessTaskResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link StartProcessResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "urn:informatica.mdm.av.xsd", name = "startProcessResponse")
    public JAXBElement<StartProcessResponse> createStartProcessResponse(StartProcessResponse value) {
        return new JAXBElement<StartProcessResponse>(_StartProcessResponse_QNAME, StartProcessResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "urn:informatica.mdm.av.xsd", name = "ownerUID", scope = TaskData.class)
    public JAXBElement<String> createTaskDataOwnerUID(String value) {
        return new JAXBElement<String>(_TaskDataOwnerUID_QNAME, String.class, TaskData.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "urn:informatica.mdm.av.xsd", name = "status", scope = TaskData.class)
    public JAXBElement<String> createTaskDataStatus(String value) {
        return new JAXBElement<String>(_TaskDataStatus_QNAME, String.class, TaskData.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link XMLGregorianCalendar }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "urn:informatica.mdm.av.xsd", name = "createDate", scope = TaskData.class)
    public JAXBElement<XMLGregorianCalendar> createTaskDataCreateDate(XMLGregorianCalendar value) {
        return new JAXBElement<XMLGregorianCalendar>(_TaskDataCreateDate_QNAME, XMLGregorianCalendar.class, TaskData.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "urn:informatica.mdm.av.xsd", name = "taskId", scope = TaskData.class)
    public JAXBElement<String> createTaskDataTaskId(String value) {
        return new JAXBElement<String>(_TaskDataTaskId_QNAME, String.class, TaskData.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "urn:informatica.mdm.av.xsd", name = "subjectAreaUID", scope = TaskData.class)
    public JAXBElement<String> createTaskDataSubjectAreaUID(String value) {
        return new JAXBElement<String>(_TaskDataSubjectAreaUID_QNAME, String.class, TaskData.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link TaskRecords }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "urn:informatica.mdm.av.xsd", name = "taskRecords", scope = TaskData.class)
    public JAXBElement<TaskRecords> createTaskDataTaskRecords(TaskRecords value) {
        return new JAXBElement<TaskRecords>(_TaskDataTaskRecords_QNAME, TaskRecords.class, TaskData.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Integer }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "urn:informatica.mdm.av.xsd", name = "priority", scope = TaskData.class)
    public JAXBElement<Integer> createTaskDataPriority(Integer value) {
        return new JAXBElement<Integer>(_TaskDataPriority_QNAME, Integer.class, TaskData.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "urn:informatica.mdm.av.xsd", name = "title", scope = TaskData.class)
    public JAXBElement<String> createTaskDataTitle(String value) {
        return new JAXBElement<String>(_TaskDataTitle_QNAME, String.class, TaskData.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "urn:informatica.mdm.av.xsd", name = "comments", scope = TaskData.class)
    public JAXBElement<String> createTaskDataComments(String value) {
        return new JAXBElement<String>(_TaskDataComments_QNAME, String.class, TaskData.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "urn:informatica.mdm.av.xsd", name = "updatedBy", scope = TaskData.class)
    public JAXBElement<String> createTaskDataUpdatedBy(String value) {
        return new JAXBElement<String>(_TaskDataUpdatedBy_QNAME, String.class, TaskData.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "urn:informatica.mdm.av.xsd", name = "interactionId", scope = TaskData.class)
    public JAXBElement<String> createTaskDataInteractionId(String value) {
        return new JAXBElement<String>(_TaskDataInteractionId_QNAME, String.class, TaskData.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "urn:informatica.mdm.av.xsd", name = "creator", scope = TaskData.class)
    public JAXBElement<String> createTaskDataCreator(String value) {
        return new JAXBElement<String>(_TaskDataCreator_QNAME, String.class, TaskData.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link XMLGregorianCalendar }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "urn:informatica.mdm.av.xsd", name = "dueDate", scope = TaskData.class)
    public JAXBElement<XMLGregorianCalendar> createTaskDataDueDate(XMLGregorianCalendar value) {
        return new JAXBElement<XMLGregorianCalendar>(_TaskDataDueDate_QNAME, XMLGregorianCalendar.class, TaskData.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link XMLGregorianCalendar }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "urn:informatica.mdm.av.xsd", name = "lastUpdateDate", scope = TaskData.class)
    public JAXBElement<XMLGregorianCalendar> createTaskDataLastUpdateDate(XMLGregorianCalendar value) {
        return new JAXBElement<XMLGregorianCalendar>(_TaskDataLastUpdateDate_QNAME, XMLGregorianCalendar.class, TaskData.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link TaskData }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "urn:informatica.mdm.av.xsd", name = "taskData", scope = INFATask.class)
    public JAXBElement<TaskData> createINFATaskTaskData(TaskData value) {
        return new JAXBElement<TaskData>(_INFATaskTaskData_QNAME, TaskData.class, INFATask.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Integer }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "urn:informatica.mdm.av.xsd", name = "workflowVersion", scope = INFATask.class)
    public JAXBElement<Integer> createINFATaskWorkflowVersion(Integer value) {
        return new JAXBElement<Integer>(_INFATaskWorkflowVersion_QNAME, Integer.class, INFATask.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "urn:informatica.mdm.av.xsd", name = "orsId", scope = INFATask.class)
    public JAXBElement<String> createINFATaskOrsId(String value) {
        return new JAXBElement<String>(_INFATaskOrsId_QNAME, String.class, INFATask.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link TaskActions }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "urn:informatica.mdm.av.xsd", name = "actions", scope = INFATask.class)
    public JAXBElement<TaskActions> createINFATaskActions(TaskActions value) {
        return new JAXBElement<TaskActions>(_INFATaskActions_QNAME, TaskActions.class, INFATask.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "urn:informatica.mdm.av.xsd", name = "hubPassword", scope = INFATask.class)
    public JAXBElement<String> createINFATaskHubPassword(String value) {
        return new JAXBElement<String>(_INFATaskHubPassword_QNAME, String.class, INFATask.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link TaskType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "urn:informatica.mdm.av.xsd", name = "taskType", scope = INFATask.class)
    public JAXBElement<TaskType> createINFATaskTaskType(TaskType value) {
        return new JAXBElement<TaskType>(_INFATaskTaskType_QNAME, TaskType.class, INFATask.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "urn:informatica.mdm.av.xsd", name = "hubUsername", scope = INFATask.class)
    public JAXBElement<String> createINFATaskHubUsername(String value) {
        return new JAXBElement<String>(_INFATaskHubUsername_QNAME, String.class, INFATask.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link byte[]}{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "urn:informatica.mdm.av.xsd", name = "securityPayload", scope = INFATask.class)
    @XmlJavaTypeAdapter(HexBinaryAdapter.class)
    public JAXBElement<byte[]> createINFATaskSecurityPayload(byte[] value) {
        return new JAXBElement<byte[]>(_INFATaskSecurityPayload_QNAME, byte[].class, INFATask.class, ((byte[]) value));
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "urn:informatica.mdm.av.xsd", name = "rowidXref", scope = INFARecordKey.class)
    public JAXBElement<String> createINFARecordKeyRowidXref(String value) {
        return new JAXBElement<String>(_INFARecordKeyRowidXref_QNAME, String.class, INFARecordKey.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "urn:informatica.mdm.av.xsd", name = "pkeySrcObject", scope = INFARecordKey.class)
    public JAXBElement<String> createINFARecordKeyPkeySrcObject(String value) {
        return new JAXBElement<String>(_INFARecordKeyPkeySrcObject_QNAME, String.class, INFARecordKey.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "urn:informatica.mdm.av.xsd", name = "rowId", scope = INFARecordKey.class)
    public JAXBElement<String> createINFARecordKeyRowId(String value) {
        return new JAXBElement<String>(_INFARecordKeyRowId_QNAME, String.class, INFARecordKey.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "urn:informatica.mdm.av.xsd", name = "system", scope = INFARecordKey.class)
    public JAXBElement<String> createINFARecordKeySystem(String value) {
        return new JAXBElement<String>(_INFARecordKeySystem_QNAME, String.class, INFARecordKey.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "urn:informatica.mdm.av.xsd", name = "tableUID", scope = INFARecordKey.class)
    public JAXBElement<String> createINFARecordKeyTableUID(String value) {
        return new JAXBElement<String>(_INFARecordKeyTableUID_QNAME, String.class, INFARecordKey.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "urn:informatica.mdm.av.xsd", name = "description", scope = INFATaskAction.class)
    public JAXBElement<String> createINFATaskActionDescription(String value) {
        return new JAXBElement<String>(_INFATaskActionDescription_QNAME, String.class, INFATaskAction.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "urn:informatica.mdm.av.xsd", name = "name", scope = INFATaskAction.class)
    public JAXBElement<String> createINFATaskActionName(String value) {
        return new JAXBElement<String>(_INFATaskActionName_QNAME, String.class, INFATaskAction.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Boolean }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "urn:informatica.mdm.av.xsd", name = "cancelTask", scope = INFATaskAction.class)
    public JAXBElement<Boolean> createINFATaskActionCancelTask(Boolean value) {
        return new JAXBElement<Boolean>(_INFATaskActionCancelTask_QNAME, Boolean.class, INFATaskAction.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Boolean }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "urn:informatica.mdm.av.xsd", name = "closeTaskView", scope = INFATaskAction.class)
    public JAXBElement<Boolean> createINFATaskActionCloseTaskView(Boolean value) {
        return new JAXBElement<Boolean>(_INFATaskActionCloseTaskView_QNAME, Boolean.class, INFATaskAction.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Boolean }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "urn:informatica.mdm.av.xsd", name = "manualReassign", scope = INFATaskAction.class)
    public JAXBElement<Boolean> createINFATaskActionManualReassign(Boolean value) {
        return new JAXBElement<Boolean>(_INFATaskActionManualReassign_QNAME, Boolean.class, INFATaskAction.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "urn:informatica.mdm.av.xsd", name = "displayName", scope = INFATaskAction.class)
    public JAXBElement<String> createINFATaskActionDisplayName(String value) {
        return new JAXBElement<String>(_INFATaskActionDisplayName_QNAME, String.class, INFATaskAction.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "urn:informatica.mdm.av.xsd", name = "description", scope = TaskType.class)
    public JAXBElement<String> createTaskTypeDescription(String value) {
        return new JAXBElement<String>(_INFATaskActionDescription_QNAME, String.class, TaskType.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "urn:informatica.mdm.av.xsd", name = "name", scope = TaskType.class)
    public JAXBElement<String> createTaskTypeName(String value) {
        return new JAXBElement<String>(_INFATaskActionName_QNAME, String.class, TaskType.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "urn:informatica.mdm.av.xsd", name = "dataUpdateType", scope = TaskType.class)
    public JAXBElement<String> createTaskTypeDataUpdateType(String value) {
        return new JAXBElement<String>(_TaskTypeDataUpdateType_QNAME, String.class, TaskType.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "urn:informatica.mdm.av.xsd", name = "displayType", scope = TaskType.class)
    public JAXBElement<String> createTaskTypeDisplayType(String value) {
        return new JAXBElement<String>(_TaskTypeDisplayType_QNAME, String.class, TaskType.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Boolean }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "urn:informatica.mdm.av.xsd", name = "pendingBVT", scope = TaskType.class)
    public JAXBElement<Boolean> createTaskTypePendingBVT(Boolean value) {
        return new JAXBElement<Boolean>(_TaskTypePendingBVT_QNAME, Boolean.class, TaskType.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "urn:informatica.mdm.av.xsd", name = "displayName", scope = TaskType.class)
    public JAXBElement<String> createTaskTypeDisplayName(String value) {
        return new JAXBElement<String>(_INFATaskActionDisplayName_QNAME, String.class, TaskType.class, value);
    }

}
