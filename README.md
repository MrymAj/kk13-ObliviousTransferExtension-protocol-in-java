# ObliviousTransferExtension

 <h2> Summary </h2>
 
This is an implementation of the <a href="https://eprint.iacr.org/2013/491">KK13 protocol</a> written in Java. This project was made for my MSc thesis for the department of Information Technology Engineering of the <a href="http://kish.ut.ac.ir/IPPWebV1C010/English_WebUI/Default.aspx"> University of Tehran</a>, under the supervision of <a href="http://scholar.google.com/citations?user=g0JaizAAAAAJ&hl=en">Dr. Salman Niksefat</a>. This project is distributed under the <a href="http://www.gnu.org/licenses/agpl-3.0.txt">GNU AFFERO GENERAL PUBLIC LICENSE</a>. 


 <h2> Running Instructions </h2>
 
 First, run the <b> RecieverMain </b> Class and then run the <b>SenderMain </b> Class with the same address.
 
 To configure the number of OT1oon, edit the <b>m </b> value in both the<b> RecieverMain </b> and <b>SenderMain </b> classes. 
 
 <h3> Note: </h3>
 
 <ul type="square">
 
 <li>the <b>m </b>value must be divisible by 8.</li>
 
 <li>the <b>m,n,k </b>values must be the same in both classes.</li>
 
 <li>the <b>n </b>value must be less than or equal to <b>security parameter k</b> that satisfies the <b>k>=n</b> condition in the kk13 algorithm.</li>

 </ul>
 
 <h2> Protocol Details </h2>
 
 Paper Title: Improved OT Extension for Transferring Short Secrets
 
 Authors: Vladimir Kolesnikov and Ranjit Kumaresan
 
 Book title: Advances in Cryptology - CRYPTO 2003 
 
 Book Subtitle: 33rd Annual Cryptology Conference, Santa Barbara, CA, USA, August 18-22, 2013. Proceedings, Part II, Pages  54-70.
 
 Copyright: 2013
 
   Link:<a href="https://www.iacr.org/cryptodb/data/paper.php?pubkey=24603"> https://www.iacr.org/cryptodb/data/paper.php?pubkey=24603 </a>

<h2>Copying and License</h2>

This material is copyright (c) 2016.

It is open and licensed under the GNU Affero General Public License (AGPL) v3.0 whose full text may be found <a href="http://www.gnu.org/licenses/agpl-3.0.txt">here</a>. 

 
