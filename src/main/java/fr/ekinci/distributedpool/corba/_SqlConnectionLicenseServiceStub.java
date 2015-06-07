package fr.ekinci.distributedpool.corba;


/**
* fr/isidis/pw/impl/distributedpool/corba/_SqlConnectionLicenseServiceStub.java .
* Generated by the IDL-to-Java compiler (portable), version "3.2"
* from SqlConnectionLicenseService.idl
* dimanche 19 avril 2015 11 h 44 CEST
*/

public class _SqlConnectionLicenseServiceStub extends org.omg.CORBA.portable.ObjectImpl implements fr.ekinci.distributedpool.corba.SqlConnectionLicenseService
{

  public fr.ekinci.distributedpool.corba.SqlNodeInformation[] getConnectionInformation (String login, String password)
  {
            org.omg.CORBA.portable.InputStream $in = null;
            try {
                org.omg.CORBA.portable.OutputStream $out = _request ("getConnectionInformation", true);
                $out.write_string (login);
                $out.write_string (password);
                $in = _invoke ($out);
                fr.ekinci.distributedpool.corba.SqlNodeInformation $result[] = fr.ekinci.distributedpool.corba.TabSqlNodeInformationHelper.read ($in);
                return $result;
            } catch (org.omg.CORBA.portable.ApplicationException $ex) {
                $in = $ex.getInputStream ();
                String _id = $ex.getId ();
                throw new org.omg.CORBA.MARSHAL (_id);
            } catch (org.omg.CORBA.portable.RemarshalException $rm) {
                return getConnectionInformation (login, password        );
            } finally {
                _releaseReply ($in);
            }
  } // getConnectionInformation

  // Type-specific CORBA::Object operations
  private static String[] __ids = {
    "IDL:fr/isidis/pw/impl/distributedpool/corba/SqlConnectionLicenseService:1.0"};

  public String[] _ids ()
  {
    return (String[])__ids.clone ();
  }

  private void readObject (java.io.ObjectInputStream s) throws java.io.IOException
  {
     String str = s.readUTF ();
     String[] args = null;
     java.util.Properties props = null;
     org.omg.CORBA.ORB orb = org.omg.CORBA.ORB.init (args, props);
   try {
     org.omg.CORBA.Object obj = orb.string_to_object (str);
     org.omg.CORBA.portable.Delegate delegate = ((org.omg.CORBA.portable.ObjectImpl) obj)._get_delegate ();
     _set_delegate (delegate);
   } finally {
     orb.destroy() ;
   }
  }

  private void writeObject (java.io.ObjectOutputStream s) throws java.io.IOException
  {
     String[] args = null;
     java.util.Properties props = null;
     org.omg.CORBA.ORB orb = org.omg.CORBA.ORB.init (args, props);
   try {
     String str = orb.object_to_string (this);
     s.writeUTF (str);
   } finally {
     orb.destroy() ;
   }
  }
} // class _SqlConnectionLicenseServiceStub