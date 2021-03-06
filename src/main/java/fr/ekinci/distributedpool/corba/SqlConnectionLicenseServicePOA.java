package fr.ekinci.distributedpool.corba;


/**
* fr/isidis/pw/impl/distributedpool/corba/SqlConnectionLicenseServicePOA.java .
* Generated by the IDL-to-Java compiler (portable), version "3.2"
* from SqlConnectionLicenseService.idl
* dimanche 19 avril 2015 11 h 44 CEST
*/

public abstract class SqlConnectionLicenseServicePOA extends org.omg.PortableServer.Servant
 implements fr.ekinci.distributedpool.corba.SqlConnectionLicenseServiceOperations, org.omg.CORBA.portable.InvokeHandler
{

  // Constructors

  private static java.util.Hashtable _methods = new java.util.Hashtable ();
  static
  {
    _methods.put ("getConnectionInformation", new java.lang.Integer (0));
  }

  public org.omg.CORBA.portable.OutputStream _invoke (String $method,
                                org.omg.CORBA.portable.InputStream in,
                                org.omg.CORBA.portable.ResponseHandler $rh)
  {
    org.omg.CORBA.portable.OutputStream out = null;
    java.lang.Integer __method = (java.lang.Integer)_methods.get ($method);
    if (__method == null)
      throw new org.omg.CORBA.BAD_OPERATION (0, org.omg.CORBA.CompletionStatus.COMPLETED_MAYBE);

    switch (__method.intValue ())
    {
       case 0:  // fr/isidis/pw/impl/distributedpool/corba/SqlConnectionLicenseService/getConnectionInformation
       {
         String login = in.read_string ();
         String password = in.read_string ();
         fr.ekinci.distributedpool.corba.SqlNodeInformation $result[] = null;
         $result = this.getConnectionInformation (login, password);
         out = $rh.createReply();
         fr.ekinci.distributedpool.corba.TabSqlNodeInformationHelper.write (out, $result);
         break;
       }

       default:
         throw new org.omg.CORBA.BAD_OPERATION (0, org.omg.CORBA.CompletionStatus.COMPLETED_MAYBE);
    }

    return out;
  } // _invoke

  // Type-specific CORBA::Object operations
  private static String[] __ids = {
    "IDL:fr/isidis/pw/impl/distributedpool/corba/SqlConnectionLicenseService:1.0"};

  public String[] _all_interfaces (org.omg.PortableServer.POA poa, byte[] objectId)
  {
    return (String[])__ids.clone ();
  }

  public SqlConnectionLicenseService _this() 
  {
    return SqlConnectionLicenseServiceHelper.narrow(
    super._this_object());
  }

  public SqlConnectionLicenseService _this(org.omg.CORBA.ORB orb) 
  {
    return SqlConnectionLicenseServiceHelper.narrow(
    super._this_object(orb));
  }


} // class SqlConnectionLicenseServicePOA
